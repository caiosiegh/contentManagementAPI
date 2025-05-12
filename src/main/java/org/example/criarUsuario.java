package org.example;

import com.github.javafaker.Faker;

public class criarUsuario {

    static Faker faker = new Faker();

    public static Usuario criarUsuarioValido() {
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(faker.name().fullName());
        usuario.setNomeUsuario(faker.name().username());
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setSenha(faker.internet().password(15, 20, true, true));
        return usuario;
    }
}
