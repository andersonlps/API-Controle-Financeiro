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
        message.setText("\n Este é seu token de redefinição de senha \n "+
                  "copie e cole no seu app\n" 
                                       +  
                                       texto +  "\n\n\n\n"
                             + "Atenciosamente \n      Gestão Financeira Pessoal\n");
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
                        "\n Atenciosamente \n    Gestão Financeira Pessoal\n");
                        
        javaMailSender.send(message);
    }
}