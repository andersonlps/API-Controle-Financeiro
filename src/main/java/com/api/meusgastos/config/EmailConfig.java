package com.api.meusgastos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


@Configuration
public class EmailConfig {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String para, String assunto, String texto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("meusgastos2023@gmail.com");
        message.setTo(para);
        message.setSubject(assunto);
        message.setText(texto);
        message.setText("\n Este é seu link de redefinição de senha \n "+
                    "\n Clique no Link abaixo \n"+
                  "copie e cole na barra de endereço do seu navegador\n" 
                                       + texto +  "\n\n\n\n"
                             + "Atenciosamente \n      Meus Gastos\n");
        javaMailSender.send(message);
    }

    public void sendEmailCadastroUser(String para, String nome) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("meusgastos2023@gmail.com");
        message.setTo(para);
        message.setSubject("Cadastro de Usuario Realizado!");
        message.setText(nome);
        message.setText("\n Olá, "+ nome +"!" + "\n"+
                        "\n Seu usuario foi cadastrado com sucesso!" +
                        "\n Seu login será com seu email: " + para +        
                        "\n Atenciosamente \n    Meus Gastos\n");
                        
                             System.out.println("teste mail");
        javaMailSender.send(message);
    }
}