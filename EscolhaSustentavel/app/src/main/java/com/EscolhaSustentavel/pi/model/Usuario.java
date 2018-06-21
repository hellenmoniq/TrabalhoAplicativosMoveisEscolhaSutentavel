package com.EscolhaSustentavel.pi.model;


public class Usuario {

    private int id;
    private String nome;
    private String nickname;
    private String senha;
    private String palavraseg;
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {

        return nome;
    }

    public void setNome(String nome) {

        this.nome = nome;
    }


    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }


    public String getNickname() {

        return nickname;
    }

    public void setNickname(String nickname) {

        this.nickname = nickname;
    }

    public String getSenha() {

        return senha;
    }

    public void setSenha(String senha) {

        this.senha = senha;
    }

    public String getPalavraseg() {

        return palavraseg;
    }

    public void setPalavraseg(String palavraseg) {

        this.palavraseg = palavraseg;
    }


}
