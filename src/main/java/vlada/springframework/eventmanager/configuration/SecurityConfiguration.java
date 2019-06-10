package vlada.springframework.eventmanager.configuration;


import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    
    @Autowired
    private DataSource dataSource;


    @Value("${spring.queries.korisnici-query}")
    private String korisniciQuery;

    @Value("${spring.queries.tipovi-query}")
    private String tipoviKorisnikaQuery;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(korisniciQuery)
                .authoritiesByUsernameQuery(tipoviKorisnikaQuery)
                .passwordEncoder(passwordEncoder());


    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // TODO Auto-generated method stub
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/registracija","/potvrdi_nalog","/zaboravljena_lozinka","/reset_potvrdjen","/reset_lozinka","/", "/css/**","/images/**", "/webjars/**").permitAll()
                .antMatchers("/profile","/prikazi_zadatak/**").authenticated()
                .antMatchers("/korisnici","/dodajZadatak","/kreirajAdmina","/kreirajRukovodioca").hasAuthority("ADMIN").anyRequest().authenticated() // ADMIN
                .and().formLogin().loginPage("/login").permitAll()
                .defaultSuccessUrl("/profile").and().logout().logoutSuccessUrl("/login");
    }


    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }







}
