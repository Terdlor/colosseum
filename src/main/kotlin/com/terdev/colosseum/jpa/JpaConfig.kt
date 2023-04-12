package com.terdev.colosseum.jpa

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import java.util.*
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories("com.terdev.colosseum.jpa.dao")
class JpaConfig {

    companion object {
        const val PACKAGE_SCAN_ENTITY = "com.terdev.colosseum.jpa.entity"
    }

    @Bean
    fun entityManagerFactory(dataSource: DataSource?): LocalContainerEntityManagerFactoryBean? {
        val emf = LocalContainerEntityManagerFactoryBean()
        emf.dataSource = dataSource!!
        emf.setPackagesToScan(PACKAGE_SCAN_ENTITY)
        val vendorAdapter: JpaVendorAdapter = HibernateJpaVendorAdapter()
        emf.jpaVendorAdapter = vendorAdapter
        val properties = Properties()
        properties.setProperty("hibernate.show_sql", "true")
        emf.setJpaProperties(properties)
        return emf
    }

    @Bean
    fun transactionManager(emf: EntityManagerFactory?): PlatformTransactionManager? {
        return JpaTransactionManager(emf!!)
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    fun hikariConfig() = HikariConfig()

    @Bean
    fun dataSource() = HikariDataSource(hikariConfig())

}