package com.fu.PE_Fall2026_DE200388.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="device_code",columnDefinition = "varchar(6)",nullable = false,unique = true)
    private String deviceCode;
    @Column(name="device_name",nullable = false, columnDefinition = "nvarchar(200)")
    private String deviceName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;
    @Column(name="price",nullable = false)
    private BigDecimal price;
    @Column(name="warranty_months",nullable = false)
    private int warrantyMonths;
    @Column(name="installation_date",nullable = false)
    private LocalDate installationDate;
    @Column(name="status",nullable = false)
    private String status;


}
