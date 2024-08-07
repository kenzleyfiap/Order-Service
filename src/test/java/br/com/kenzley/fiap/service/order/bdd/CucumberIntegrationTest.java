package br.com.kenzley.fiap.service.order.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;

@CucumberContextConfiguration
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "br.com.kenzley.fiap.service.order.bdd", // Pacote onde estão as classes Steps Definitions
        plugin = {"pretty", "html:target/cucumber-reports.html"} // Configuração de relatórios
)
public class CucumberIntegrationTest {

}
