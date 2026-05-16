package com.zjw.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI 3.0 配置类
 */
@Configuration
public class OpenApiConfig {

  /**
   * 自定义 OpenAPI 文档信息
   *
   * @return OpenAPI 实例
   */
  @Bean
  public OpenAPI customOpenApi() {
    Contact contact = new Contact()
        .name("zjw")
        .email("zjw@example.com");
    License license = new License()
        .name("Apache 2.0")
        .url("https://www.apache.org/licenses/LICENSE-2.0");
    Info info = new Info()
        .title("多 JVM 语言 Spring Boot API 文档")
        .description("本项目展示了在 Spring Boot 4.0 中同时使用 Java、Scala、Groovy、Kotlin 四种 JVM 语言进行开发。")
        .version("1.0.0")
        .contact(contact)
        .license(license);
    return new OpenAPI().info(info);
  }
}
