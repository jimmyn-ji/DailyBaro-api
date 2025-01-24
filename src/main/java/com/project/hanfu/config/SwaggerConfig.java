package com.project.hanfu.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@Data
@ConfigurationProperties(prefix = "swagger")
@ConditionalOnProperty(prefix = "swagger",name = "open",havingValue = "true")
public class SwaggerConfig {


    //"com.lightmes.production.controller"
    private String basePackage;
    //
    private String title;

    private String description;



    @Bean
    public Docket docketWeb() {
        List<Parameter> pars = getParameters();

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.ant("/web/**")).build().groupName("web使用接口")
                .pathMapping("/").globalOperationParameters(pars);

    }

    private List<Parameter> getParameters() {
        //添加参数选项
        ParameterBuilder tokenPar = new ParameterBuilder();
        //ParameterBuilder versionPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        //版本号参数
        //versionPar.name("version").description("版本号").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        //在请求头中设置token参数
        tokenPar.name("token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        //pars.add(versionPar.build());
        pars.add(tokenPar.build());
        return pars;
    }


    //构建api文档的详细信息函数
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title(title)
                //创建人
                .contact(new Contact("Bevan", null, ""))
                //版本号
                .version("1.0")
                //描述
                .description(description)
                .build();
    }






}
