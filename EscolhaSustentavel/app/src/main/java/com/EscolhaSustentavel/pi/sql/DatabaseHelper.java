package com.EscolhaSustentavel.pi.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.EscolhaSustentavel.pi.model.Produto;
import com.EscolhaSustentavel.pi.model.Usuario;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 8;
    private static final String DATABASE_NAME = "BancoPi.db";

    // Usuário
    private static final String TABELA_Usuario = "usuario";

    private static final String COLUNA_Usuario_ID = "user_id";
    private static final String COLUNA_Usuario_NOME = "user_nome";
    private static final String COLUNA_USuario_NICKNAME = "user_nickname";
    private static final String COLUNA_Usuario_SENHA = "user_senha";
    private static final String COLUNA_Usuario_PalavraS = "user_palavras";

    // Produto
    private static final String TABELA_PRODUTO = "produto";

    private static final String COLUNA_PRODUTO_ID = "produto_id";
    private static final String COLUNA_PRODUTO_NOME = "produto_nome";
    private static final String COLUNA_PRODUTO_DESCRICAO = "produto_desc";
    private static final String COLUNA_PRODUTO_COMPOSICAO = "produto_comp";
    private static final String COLUNA_PRODUTO_TEMPODECOMP = "produto_tempodecomp";
    private static final String COLUNA_PRODUTO_IMPACTO = "produto_impacto";
    private static final String COLUNA_PRODUTO_CATEGORIA = "produto_categoria";
    private static final String COLUNA_PRODUTO_FAVORITE = "produto_favorite";

    // aqui vai ser as colunas para trabalhar com o google maps
    private static final String COLUNA_PRODUTO_LAT = "produto_lat";
    private static final String COLUNA_PRODUTO_LON = "produto_lon";
    private static final String COLUNA_PRODUTO_URL = "produto_url";

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABELA_Usuario
            + "(" + COLUNA_Usuario_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUNA_Usuario_NOME + " TEXT,"
            + COLUNA_USuario_NICKNAME + " TEXT,"
            + COLUNA_Usuario_SENHA + " TEXT,"
            + COLUNA_Usuario_PalavraS + " TEXT"
            + ")";

    private String CREATE_PRODUTO_TABLE = "CREATE TABLE " + TABELA_PRODUTO
            + "(" + COLUNA_PRODUTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUNA_PRODUTO_NOME + " TEXT,"
            + COLUNA_PRODUTO_DESCRICAO + " TEXT,"
            + COLUNA_PRODUTO_COMPOSICAO + " TEXT,"
            + COLUNA_PRODUTO_TEMPODECOMP + " TEXT,"
            + COLUNA_PRODUTO_IMPACTO + " TEXT,"
            + COLUNA_PRODUTO_CATEGORIA + " TEXT,"
            + COLUNA_PRODUTO_LAT + " TEXT,"
            + COLUNA_PRODUTO_LON + " TEXT,"
            + COLUNA_PRODUTO_URL + " TEXT,"
            + COLUNA_PRODUTO_FAVORITE + " INTEGER DEFAULT 0"
            + ")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABELA_Usuario;
    private String DROP_PRODUTO_TABLE = "DROP TABLE IF EXISTS " + TABELA_PRODUTO;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Apenas para testes - Remover apos ajustes
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_PRODUTO_TABLE);

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PRODUTO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_PRODUTO_TABLE);
        onCreate(db);
    }


    // USUÁRIO
    public void addUser(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUNA_Usuario_NOME, usuario.getNome());
        values.put(COLUNA_USuario_NICKNAME, usuario.getNickname());
        values.put(COLUNA_Usuario_SENHA, usuario.getSenha());
        values.put(COLUNA_Usuario_PalavraS, usuario.getPalavraseg());

        db.insert(TABELA_Usuario, null, values);
        db.close();
    }

    public List<Usuario> getAllUser() {
        String[] columns = {
                COLUNA_Usuario_ID,
                COLUNA_USuario_NICKNAME,
                COLUNA_Usuario_NOME,
                COLUNA_Usuario_SENHA,
                COLUNA_Usuario_PalavraS
        };

        String sortOrder = COLUNA_Usuario_NOME + " ASC";
        List<Usuario> usuarioList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_Usuario,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()) {
            do {
                Usuario usuario = new Usuario();
                usuario.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUNA_Usuario_ID))));
                usuario.setNome(cursor.getString(cursor.getColumnIndex(COLUNA_Usuario_NOME)));
                usuario.setNickname(cursor.getString(cursor.getColumnIndex(COLUNA_USuario_NICKNAME)));
                usuario.setSenha(cursor.getString(cursor.getColumnIndex(COLUNA_Usuario_SENHA)));
                usuario.setPalavraseg(cursor.getString(cursor.getColumnIndex(COLUNA_Usuario_PalavraS)));
                usuarioList.add(usuario);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return usuarioList;
    }

    public void deleteUser(String nick, String Palavras) {

        SQLiteDatabase db = this.getReadableDatabase();

        String strSQL = "DELETE FROM  usuario WHERE user_nickname= " + "'" + nick + "'" +
                "and user_palavras = " + "'" + Palavras + "'";

        db.execSQL(strSQL);
    }


    public boolean checkUser(String email) {
        String[] columns = {
                COLUNA_Usuario_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUNA_USuario_NICKNAME + " = ?";

        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABELA_Usuario,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public boolean checkUser(String nickname, String senha) {
        String[] columns = {
                COLUNA_Usuario_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUNA_USuario_NICKNAME + " = ?" + " AND " + COLUNA_Usuario_SENHA + " = ?";
        String[] selectionArgs = {nickname, senha};

        Cursor cursor = db.query(TABELA_Usuario,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


    // PRODUTOS
    public void addProduct(Produto produto) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUNA_PRODUTO_NOME, produto.getNameProduct());
        values.put(COLUNA_PRODUTO_DESCRICAO, produto.getDescProduct());
        values.put(COLUNA_PRODUTO_COMPOSICAO, produto.getCompProduct());
        values.put(COLUNA_PRODUTO_TEMPODECOMP, produto.getTempoProduct());
        values.put(COLUNA_PRODUTO_IMPACTO, produto.getImpactProduct());
        values.put(COLUNA_PRODUTO_CATEGORIA, produto.getCategoryProduct());
        values.put(COLUNA_PRODUTO_LAT, produto.getLatProduct());
        values.put(COLUNA_PRODUTO_LON, produto.getLonProduct());
        values.put(COLUNA_PRODUTO_URL, produto.getUrlProduct());

        db.insert(TABELA_PRODUTO, null, values);
        db.close();
    }

    public List<Produto> getAllProduto(String category) {
        String[] columns = {
                COLUNA_PRODUTO_ID,
                COLUNA_PRODUTO_NOME,
                COLUNA_PRODUTO_DESCRICAO,
                COLUNA_PRODUTO_COMPOSICAO,
                COLUNA_PRODUTO_TEMPODECOMP,
                COLUNA_PRODUTO_IMPACTO,
                COLUNA_PRODUTO_CATEGORIA,
                COLUNA_PRODUTO_LAT,
                COLUNA_PRODUTO_LON,
                COLUNA_PRODUTO_URL,
                COLUNA_PRODUTO_FAVORITE
        };

        String sortOrder = COLUNA_PRODUTO_NOME + " ASC";
        List<Produto> usuarioProd = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_PRODUTO,
                columns,
                COLUNA_PRODUTO_CATEGORIA + " = ?",
                new String[]{category},
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()) {
            do {
                Produto produto = new Produto();
                produto.setIdProduct(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_ID))));
                produto.setNameProduct(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_NOME)));
                produto.setDescProduct(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_DESCRICAO)));
                produto.setCompProduct(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_COMPOSICAO)));
                produto.setTempoProduct(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_TEMPODECOMP)));
                produto.setImpactProduct(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_IMPACTO)));
                produto.setCategoryProduct(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_CATEGORIA)));
                produto.setLatProduct(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_LAT))));
                produto.setLonProduct(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_LON))));
                produto.setUrlProduct(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_URL)));
                produto.setFavorite(cursor.getInt(cursor.getColumnIndex(COLUNA_PRODUTO_FAVORITE)) != 0);
                usuarioProd.add(produto);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return usuarioProd;
    }

    public List<String> getAllCategory() {
        String[] columns = {
                COLUNA_PRODUTO_CATEGORIA
        };

        String sortOrder = COLUNA_PRODUTO_CATEGORIA + " ASC";
        List<String> usuarioProd = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_PRODUTO,
                columns,
                null,
                null,
                COLUNA_PRODUTO_CATEGORIA,
                null,
                sortOrder);

        if (cursor.moveToFirst()) {
            do {
                Produto produto = new Produto();
                usuarioProd.add(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_CATEGORIA)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return usuarioProd;
    }

    public boolean updateProductForFavorite(int idProduct, int status) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUNA_PRODUTO_FAVORITE, status);

// Which row to update, based on the title
        String selection = COLUNA_PRODUTO_ID + " = ?";
        String[] selectionArgs = {String.valueOf(idProduct)};

        int count = db.update(
                TABELA_PRODUTO,
                values,
                selection,
                selectionArgs);

        Log.i("Script", "updateProductForFavorite: " + count);

        if (count == 1) {
            return true;
        } else {
            return false;
        }
    }


    public List<Produto> getAllProdutoInFavorites(String category, int status) {
        String[] columns = {
                COLUNA_PRODUTO_ID,
                COLUNA_PRODUTO_NOME,
                COLUNA_PRODUTO_DESCRICAO,
                COLUNA_PRODUTO_COMPOSICAO,
                COLUNA_PRODUTO_TEMPODECOMP,
                COLUNA_PRODUTO_IMPACTO,
                COLUNA_PRODUTO_CATEGORIA,
                COLUNA_PRODUTO_LAT,
                COLUNA_PRODUTO_LON,
                COLUNA_PRODUTO_URL
        };

        String sortOrder = COLUNA_PRODUTO_NOME + " ASC";
        List<Produto> usuarioProd = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_PRODUTO,
                columns,
                COLUNA_PRODUTO_CATEGORIA + " = ?" + " AND " + COLUNA_PRODUTO_FAVORITE + " = ?",
                new String[]{category, String.valueOf(1)},
                COLUNA_PRODUTO_NOME,
                null,
                sortOrder);

        if (cursor.moveToFirst()) {
            do {
                Produto produto = new Produto();
                produto.setIdProduct(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_ID))));
                produto.setNameProduct(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_NOME)));
                produto.setDescProduct(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_DESCRICAO)));
                produto.setCompProduct(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_COMPOSICAO)));
                produto.setTempoProduct(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_TEMPODECOMP)));
                produto.setImpactProduct(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_IMPACTO)));
                produto.setCategoryProduct(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_CATEGORIA)));
                produto.setLatProduct(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_LAT))));
                produto.setLonProduct(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_LON))));
                produto.setUrlProduct(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_URL)));
                usuarioProd.add(produto);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return usuarioProd;
    }


    // Util
    public void clearData() {
        SQLiteDatabase db = this.getWritableDatabase();
        // db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_PRODUTO_TABLE);

        // db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PRODUTO_TABLE);
    }


    //método que irá checar se a palavra de segurança pertence ao usuario

    public boolean CheckPalavra(String nickname, String palavras) {

        String[] columns = {
                COLUNA_Usuario_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUNA_USuario_NICKNAME + " = ?" + " AND " + COLUNA_Usuario_PalavraS + " = ?";

        String[] selectionArgs = {nickname, palavras};


        Cursor cursor = db.query(TABELA_Usuario,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;

    }


    //método para alterar a senha do usuario

    public void AlterarSenha(String Nikcname, String PalavraS, String Novasenha) {


        SQLiteDatabase db = this.getReadableDatabase();

        String strSQL = "UPDATE usuario SET user_senha= " + "'" + Novasenha + "'" +
                " WHERE user_nickname= " + "'" + Nikcname + "'" + " and user_palavras = " + "'" + PalavraS + "'";

        db.execSQL(strSQL);

    }

    public void AlterarNome(String Nikcname, String PalavraS, String Novonome) {


        SQLiteDatabase db = this.getReadableDatabase();

        String strSQL = "UPDATE usuario SET user_nome= " + "'" + Novonome + "'" +
                " WHERE user_nickname= " + "'" + Nikcname + "'" + " and user_palavras = " + "'" + PalavraS + "'";

        db.execSQL(strSQL);

    }


    //Listando todos os produtos com o mesmo nome e de categorias que não foram escolhidas pelo usuario

    public List<String> ListarCategorias(String Categoria, String Produto) {

        String[] columns = {
                COLUNA_PRODUTO_CATEGORIA //caso não dê certo por o nome do produto também
        };

        String sortOrder = COLUNA_PRODUTO_CATEGORIA + " ASC";
        List<String> usuarioProd = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_PRODUTO,
                columns,
                COLUNA_PRODUTO_CATEGORIA + " <> ?" + " AND " + COLUNA_PRODUTO_NOME + " = ?",
                new String[]{Categoria, Produto},
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()) {
            do {
                Produto Produt = new Produto();
                usuarioProd.add(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_CATEGORIA)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return usuarioProd;

    }


    //listar um produto de uma determinada categoria
    public List<String> ListarProduto(String categoria, String produto) {
        String[] columns = {
                COLUNA_PRODUTO_ID,
                COLUNA_PRODUTO_NOME,
                COLUNA_PRODUTO_DESCRICAO,
                COLUNA_PRODUTO_COMPOSICAO,
                COLUNA_PRODUTO_TEMPODECOMP,
                COLUNA_PRODUTO_IMPACTO,
                COLUNA_PRODUTO_CATEGORIA,
                COLUNA_PRODUTO_LAT,
                COLUNA_PRODUTO_LON,
                COLUNA_PRODUTO_URL,
                COLUNA_PRODUTO_FAVORITE
        };

        String sortOrder = COLUNA_PRODUTO_NOME + " ASC";
        List<String> usuarioProd = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_PRODUTO,
                columns,
                COLUNA_PRODUTO_CATEGORIA + " = ?" + " AND " + COLUNA_PRODUTO_NOME + " = ?",
                new String[]{categoria, produto},
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()) {
            do {
                usuarioProd.add(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUNA_PRODUTO_ID))));
                usuarioProd.add(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_CATEGORIA)));
                usuarioProd.add(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_IMPACTO)));
                usuarioProd.add(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_TEMPODECOMP)));
                usuarioProd.add(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_COMPOSICAO)));
                usuarioProd.add(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_DESCRICAO)));
                usuarioProd.add(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_NOME)));
                usuarioProd.add(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_URL)));
                usuarioProd.add(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUNA_PRODUTO_FAVORITE))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return usuarioProd;
    }

    //listar um produto de uma determinada categoria
    public List<String> ListarProdutoByFavorite(String categoria, String produto) {
        String[] columns = {
                COLUNA_PRODUTO_ID,
                COLUNA_PRODUTO_NOME,
                COLUNA_PRODUTO_DESCRICAO,
                COLUNA_PRODUTO_COMPOSICAO,
                COLUNA_PRODUTO_TEMPODECOMP,
                COLUNA_PRODUTO_IMPACTO,
                COLUNA_PRODUTO_CATEGORIA,
                COLUNA_PRODUTO_LAT,
                COLUNA_PRODUTO_LON,
                COLUNA_PRODUTO_URL,
                COLUNA_PRODUTO_FAVORITE
        };

        String sortOrder = COLUNA_PRODUTO_NOME + " ASC";
        List<String> usuarioProd = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_PRODUTO,
                columns,
                COLUNA_PRODUTO_CATEGORIA + " = ?" + " AND " + COLUNA_PRODUTO_FAVORITE + " = ?",
                new String[]{categoria, String.valueOf(1)},
                COLUNA_PRODUTO_NOME,
                null,
                sortOrder);

        if (cursor.moveToFirst()) {
            do {
                usuarioProd.add(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUNA_PRODUTO_ID))));
                usuarioProd.add(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_CATEGORIA)));
                usuarioProd.add(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_IMPACTO)));
                usuarioProd.add(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_TEMPODECOMP)));
                usuarioProd.add(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_COMPOSICAO)));
                usuarioProd.add(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_DESCRICAO)));
                usuarioProd.add(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_NOME)));
                usuarioProd.add(cursor.getString(cursor.getColumnIndex(COLUNA_PRODUTO_URL)));
                usuarioProd.add(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUNA_PRODUTO_FAVORITE))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return usuarioProd;
    }

}
