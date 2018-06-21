package com.EscolhaSustentavel.pi.model;

/**
 * Created by Marcio Ygor on 24/05/2018.
 */

public class Produto {


    private int idProduct;
    private String nameProduct; //nome
    private String descProduct; //descrição
    private String compProduct; //composição
    private String tempoProduct; //tempo de decomposição
    private String impactProduct; //impacto no meio ambiente
    private String categoryProduct; //categoria
    private boolean isFavorite; //favorito
    private String urlProduct; //caminho da imagem do produto
    private Double latProduct; //latitude (para usar no google maps)
    private Double lonProduct; //longitude para usar no google maps)

    public Produto() {

    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getDescProduct() {
        return descProduct;
    }

    public void setDescProduct(String descProduct) {
        this.descProduct = descProduct;
    }

    public String getCompProduct() {
        return compProduct;
    }

    public void setCompProduct(String compProduct) {
        this.compProduct = compProduct;
    }

    public String getTempoProduct() {
        return tempoProduct;
    }

    public void setTempoProduct(String tempoProduct) {
        this.tempoProduct = tempoProduct;
    }

    public String getImpactProduct() {
        return impactProduct;
    }

    public void setImpactProduct(String impactProduct) {
        this.impactProduct = impactProduct;
    }

    public Double getLatProduct() {
        return latProduct;
    }

    public void setLatProduct(Double latProduct) {
        this.latProduct = latProduct;
    }

    public Double getLonProduct() {
        return lonProduct;
    }

    public void setLonProduct(Double lonProduct) {
        this.lonProduct = lonProduct;
    }

    public String getCategoryProduct() {
        return categoryProduct;
    }

    public void setCategoryProduct(String categoryProduct) {
        this.categoryProduct = categoryProduct;
    }

    public String getUrlProduct() {
        return urlProduct;
    }

    public void setUrlProduct(String urlProduct) {
        this.urlProduct = urlProduct;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}