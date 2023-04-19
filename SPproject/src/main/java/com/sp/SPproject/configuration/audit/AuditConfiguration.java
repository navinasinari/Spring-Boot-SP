package com.sp.SPproject.configuration.audit;

import org.audit4j.core.MetaData;
import org.audit4j.core.handler.ConsoleAuditHandler;
import org.audit4j.core.handler.Handler;
import org.audit4j.core.handler.file.FileAuditHandler;
import org.audit4j.core.layout.Layout;
import org.audit4j.core.layout.SimpleLayout;
import org.audit4j.integration.spring.AuditAspect;
import org.audit4j.integration.spring.SpringAudit4jConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableAspectJAutoProxy
public class AuditConfiguration {

    @Bean
    public Layout layout() {
        return new SimpleLayout();
    }

    @Bean
    public MetaData metaData() {
        return new CustomMetaData();
    }

    @Bean
    public FileAuditHandler fileAuditHandler() {
        FileAuditHandler fileAuditHandler = new FileAuditHandler();
        return fileAuditHandler;
    }

    @Bean
    public ConsoleAuditHandler consoleAuditHandler() {
        return new ConsoleAuditHandler();
    }

    @Bean
    public SpringAudit4jConfig springAudit4jConfig() {
        SpringAudit4jConfig audit4jConfig = new SpringAudit4jConfig();
        Map<String, String> props = new HashMap<>();
        props.put("log.file.location", "./SPproject/src/main/java/com/sp/SPproject/logs");
        List<Handler> handlers = new ArrayList<>();
        handlers.add(consoleAuditHandler());
        handlers.add(fileAuditHandler());
        audit4jConfig.setHandlers(handlers);
        audit4jConfig.setLayout(layout());
        audit4jConfig.setMetaData(metaData());
        audit4jConfig.setProperties(props);
        return audit4jConfig;
    }

    @Bean
    public AuditAspect auditAspect() {
        return new AuditAspect();
    }
}
