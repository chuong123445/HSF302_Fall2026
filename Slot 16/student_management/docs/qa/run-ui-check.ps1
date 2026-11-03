param([string]$RepositoryPath = '')
$ErrorActionPreference = 'Stop'
$projectRoot = (Resolve-Path (Join-Path $PSScriptRoot '../..')).Path
$mavenOptions = @()
if ($RepositoryPath) { $mavenOptions += "-Dmaven.repo.local=$RepositoryPath" }
Push-Location $projectRoot
try {
    & mvn @mavenOptions -q clean install
    if ($LASTEXITCODE -ne 0) { throw 'Maven clean install failed' }
    $qaOutputDir = Join-Path $projectRoot 'ui-module/target/ui-check'
    New-Item -ItemType Directory -Force -Path $qaOutputDir | Out-Null
    $uiClasspathFile = Join-Path $qaOutputDir 'ui-classpath.txt'
    $dataClasspathFile = Join-Path $qaOutputDir 'data-classpath.txt'
    & mvn @mavenOptions -q -f ui-module/pom.xml dependency:build-classpath "-Dmdep.outputFile=$uiClasspathFile"
    if ($LASTEXITCODE -ne 0) { throw 'Cannot resolve UI dependencies' }
    & mvn @mavenOptions -q -f data-module/pom.xml dependency:build-classpath "-Dmdep.outputFile=$dataClasspathFile"
    if ($LASTEXITCODE -ne 0) { throw 'Cannot resolve H2 test dependency' }
    $uiClasspath = (Get-Content -Raw -LiteralPath $uiClasspathFile).Trim()
    $dataClasspath = (Get-Content -Raw -LiteralPath $dataClasspathFile).Trim()
    $qaClasspath = (Join-Path $projectRoot 'ui-module/target/classes') + [IO.Path]::PathSeparator + $uiClasspath + [IO.Path]::PathSeparator + $dataClasspath + [IO.Path]::PathSeparator + $qaOutputDir
    & javac -encoding UTF-8 -cp $qaClasspath -d $qaOutputDir (Join-Path $PSScriptRoot 'Chapter9UiCheck.java')
    if ($LASTEXITCODE -ne 0) { throw 'Cannot compile JavaFX integration check' }
    & java '-Dqa.phase=styled' "-Dqa.screenshots=$(Join-Path $projectRoot 'docs/screenshots')" -cp $qaClasspath Chapter9UiCheck `
        '--spring.datasource.url=jdbc:h2:mem:chapter9;DB_CLOSE_DELAY=-1' `
        '--spring.datasource.username=sa' '--spring.datasource.password=' `
        '--spring.datasource.driver-class-name=org.h2.Driver' `
        '--spring.jpa.hibernate.ddl-auto=create-drop' '--spring.jpa.show-sql=false'
    if ($LASTEXITCODE -ne 0) { throw 'JavaFX integration check failed' }
} finally {
    Pop-Location
}
