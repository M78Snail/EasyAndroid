package com.example.dxm.easyandroid.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.dxm.easyandroid.bean.Article;
import com.example.dxm.easyandroid.bean.ArticleDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duxiaoming on 16/8/3.
 * blog：m78star.com
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_ARTICLES = "articles";
    public static final String TABLE_ARTICLES_CONTENT = "article_content";

    private static final String CREATE_ARTICLE_TABLE_SQL = "CREATE TABLE articles ( "
            + "post_id VARCHAR(10) PRIMARY KEY UNIQUE ,"
            + "kind VARCHAR(10) ,"
            + "title VARCHAR(10) ,"
            + "summary TEXT"
            + " )";
    private static final String CREATE_ARTICLES_CONTENT_TABLE_SQL = "CREATE TABLE article_content ( "
            + "post_id VARCHAR(10) PRIMARY KEY UNIQUE,"
            + "content TEXT"
            + " )";

    private static final String QUERY_TABLE_ARTICLES = "select * from " + TABLE_ARTICLES + " where kind = ?";
    private static final String QUERY_TABLE_ARTICLES_CONTENT = "select * from " + TABLE_ARTICLES_CONTENT + " where post_id = ? ";


    static final String DB_NAME = "esy_article.db";
    static final int DB_VERSION = 2;
    private SQLiteDatabase mDatabase;
    static DatabaseHelper sDatabaseHelper;

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mDatabase = getWritableDatabase();
    }

    public static DatabaseHelper getInstance(Context context) {
        if (sDatabaseHelper == null) {
            synchronized (DatabaseHelper.class) {
                if (sDatabaseHelper == null) {
                    sDatabaseHelper = new DatabaseHelper(context);
                }
            }
        }
        return sDatabaseHelper;
    }

    public void saveSingleArticle(Article article) {
        //mDatabase.insert(TABLE_ARTICLES, null, article2ContentValues(article));
        mDatabase.insertWithOnConflict(TABLE_ARTICLES, null, article2ContentValues(article), SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void saveArticles(List<Article> dataList) {
        for (Article article : dataList)
            saveSingleArticle(article);
    }

    /**
     * 加载所有文章缓存
     *
     * @return 所有文章列表
     */
    public List<Article> loadArticls() {
        Cursor cursor = mDatabase.rawQuery(QUERY_TABLE_ARTICLES, null);
        List<Article> result = parseArticles(cursor);
        cursor.close();
        return result;

    }

    private List<Article> parseArticles(Cursor cursor) {
        List<Article> articles = new ArrayList<>();
        while (cursor.moveToNext()) {
            Article item = new Article();
            item.setPostId(cursor.getString(0));
            item.setKind(cursor.getString(1));
            item.setTitle(cursor.getString(2));
            item.setSummary(cursor.getString(3));
            // 解析数据
            articles.add(item);
        }
        return articles;
    }

    private ContentValues article2ContentValues(Article item) {
        ContentValues newValues = new ContentValues();
        newValues.put("post_id", item.getPostId());
        newValues.put("kind", item.getKind());
        newValues.put("title", item.getTitle());
        newValues.put("summary", item.getSummary());
        return newValues;

    }

    /**
     * 缓存文章内容
     *
     * @param detail 文章内容
     */
    public void saveArticleDetail(ArticleDetail detail) {
        mDatabase.insertWithOnConflict(TABLE_ARTICLES_CONTENT, null,
                articleDetailtoContentValues(detail),
                SQLiteDatabase.CONFLICT_REPLACE);
    }

    /**
     * 根据文章ID加载文章内容
     *
     * @param postId 文章ID
     * @return 文章内容
     */
    public ArticleDetail loadArticleDetail(String postId) {
        ArticleDetail detail = null;
        Cursor cursor = mDatabase.rawQuery("select * from " + TABLE_ARTICLES_CONTENT
                        + " where post_id = ?"
                , new String[]{postId});
        while (cursor.moveToNext()) {
            detail = parseArticleDetail(cursor);
        }
        cursor.close();
        return detail;
    }

    /**
     * 根据种类加载数据库中的缓存
     *
     * @param kind 种类
     * @return 文章列表
     */
    public List<Article> loadArticle(String kind) {
        Cursor cursor = mDatabase.rawQuery(QUERY_TABLE_ARTICLES, new String[]{kind});
        Log.d("TAG", "has");
        List<Article> articleList = parseArticles(cursor);
        cursor.close();
        return articleList;
    }

    private ArticleDetail parseArticleDetail(Cursor cursor) {
        ArticleDetail articleDetail = new ArticleDetail();
        articleDetail.setPostId(cursor.getString(0));
        articleDetail.setContent(cursor.getString(1));
        return articleDetail;
    }

    protected ContentValues articleDetailtoContentValues(ArticleDetail detail) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("post_id", detail.getPostId());
        contentValues.put("content", detail.getContent());
        return contentValues;
    }

    public List<ArticleDetail> loadAllArticleDetails() {
        List<ArticleDetail> articleDetails = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery(QUERY_TABLE_ARTICLES_CONTENT, null);
        while (cursor.moveToNext()) {
            articleDetails.add(parseArticleDetail(cursor));
        }
        cursor.close();
        return articleDetails;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ARTICLE_TABLE_SQL);
        db.execSQL(CREATE_ARTICLES_CONTENT_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE " + TABLE_ARTICLES);
        db.execSQL("DROP TABLE " + TABLE_ARTICLES_CONTENT);
        onCreate(db);
    }

    public void delteAllArticle() {
        mDatabase.delete(TABLE_ARTICLES, null, null);
    }

    public void deleteAllArticleContent() {
        mDatabase.delete(TABLE_ARTICLES_CONTENT, null, null);
    }
}
