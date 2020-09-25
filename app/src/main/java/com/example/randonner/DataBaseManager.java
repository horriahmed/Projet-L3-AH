package com.example.randonner;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.Date;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;


public class DataBaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="Randonnee.db";
    private static final int DATABASE_VERSION=5;

    public DataBaseManager (Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {


        String sqlParcours="create table parcours("
                +"id INTEGER PRIMARY KEY,"
                +"name varchar(40) not null,"
                +"date DATE,"
                +"description TEXT)";


        db.execSQL(sqlParcours);
        String sqlPoint="create table point("
                +"latitude double,"
                +"longitude double,"
                +"id_parcours INTEGER,"
                +" constraint fk_id_parcours  FOREIGN KEY (id_parcours) REFERENCES parcours(id),"
                +"constraint pk_lat_lng PRIMARY KEY (latitude,longitude)"
                +")";

        db.execSQL(sqlPoint);

        String sqlPointInteret="create table pointInteret("
                +"name varchar(255),"
                +"description varchar(512),"
                +"latitude double,"
                +"longitude double,"
                +"constraint pk_lat_lng_PI PRIMARY KEY (latitude,longitude)"
                +")";
        db.execSQL(sqlPointInteret);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String str="drop table parcours";
        String str2="drop table point";

        db.execSQL(str);
        db.execSQL(str2);
        this.onCreate(db);
    }

    //methode finale ajout d'un parcours
    public void insertParcours(int id,String name,String description){
        name=name.replace("''","'");
        description=description.replace("''","'");
        Date date =new Date();

        String strAddParcours="insert into parcours(id,name,date,description) values(" +
                "'"+id+"','"+name+"','"+date+"','"+description+"')";
        this.getWritableDatabase().execSQL(strAddParcours);
        Toast insertParcours=Toast.makeText(getApplicationContext(),"parcours ajouté", Toast.LENGTH_SHORT);
        insertParcours.show();

    }


    //methode finale ajout d'un point
    public void insertPoint(double latitude,double longitude,int id_parcours){

        String strAddPoint="insert into point(latitude,longitude,id_parcours) values("
                +"'"+latitude+"','"+longitude+"','"+id_parcours+"')";
        this.getWritableDatabase().execSQL(strAddPoint);
        Toast insertPoint=Toast.makeText(getApplicationContext(),"point ajouté", Toast.LENGTH_SHORT);
        insertPoint.show();
    }

//methode finale ajout d'un point interet
    public void insertPointInteret(String name,String description,double lat,double lng){
        name=name.replace("''","'");
        description=description.replace("''","'");
        String strPointInteret="insert into pointInteret(name,latitude,longitude,description) values("
                +"'"+name+"','"+lat+"','"+lng+"', '"+description+"')";
        this.getWritableDatabase().execSQL(strPointInteret);
        Toast insertPI=Toast.makeText(getApplicationContext(),"point intérêt ajouté", Toast.LENGTH_SHORT);
        insertPI.show();

    }

    //ajouter une description à un point //methode finale
    public void addDescription(String description){
        description=description.replace("''","'");
        String insertDescription="insert into pointInteret(descrition) values('"+description+"'";
        this.getWritableDatabase().execSQL(insertDescription);
        System.out.println("J'ai inseré la description");
        Toast insertDescriptio=Toast.makeText(getApplicationContext(),"description ajouté", Toast.LENGTH_SHORT);
        insertDescriptio.show();
    }

    //methode finale pour modifier la description d'un point interet
    public void alterDescription(String description,double lat,double lng){
        description=description.replace("''","'");
        //name=name.replace("''","'");
        System.out.println("je vais changer");
        String strUpdate = " update pointInteret set description='"+description+"' where " +
                "latitude='"+lat+"' and longitude='"+lng+"'";
        this.getWritableDatabase().execSQL(strUpdate);
        System.out.println("J'ai modifier la description--------");
        Toast alterDescription=Toast.makeText(getApplicationContext(),"Description modifiée", Toast.LENGTH_SHORT);
        alterDescription.show();
    }

    //methode finale pour changer un le nom d'un point d'interet
    public void alterNamePoint(String name,double lat,double lng){
        name=name.replace("''","'");
        System.out.println("je vais changer le nom");
        String strUpdate = " update pointInteret set name='"+name+"' where " +
                "latitude='"+lat+"' and longitude='"+lng+"'";
        this.getWritableDatabase().execSQL(strUpdate);
        System.out.println("J'ai modifier le nom--------");
        Toast alterNamePoint=Toast.makeText(getApplicationContext(),"Nom modifié", Toast.LENGTH_SHORT);
        alterNamePoint.show();

    }

//methode finale pour recuperer la liste des point d'interets d'un utilisateur
    public ArrayList<PointInteret> listPointInteret(){
        System.out.println("-------------je suis dans la liste des points");
        // String sqlListPoint="select name from pointInteret";
        Cursor cursor=this.getWritableDatabase().query("pointInteret",new String[] {"name",
                        "description","latitude","longitude"}
        ,null,null,null,null,null);
        ArrayList<PointInteret> list=new ArrayList<>();
        cursor.moveToNext();
        System.out.println("la position est: "+cursor.getPosition());
        int count=cursor.getCount();
        for (int i=0;i<count;i++){
            list.add(new PointInteret(new LatLng(cursor.getDouble(2),cursor.getDouble(3)),
                    map,cursor.getString(0),cursor.getString(1)));
            cursor.moveToNext();
        }
        return list;
    }
//methode finale pour recuperer le nom d'un point interet
    public String getPointName(double lat, double lng){
        Cursor cursor=this.getWritableDatabase().query("pointInteret",new String[] {"name"},
                "latitude='"+lat+"' and longitude='"+lng+"'",null,
                null,null,null);
        cursor.moveToNext();
        System.out.println("Name-------------"+cursor.getString(cursor.getPosition()));
        return cursor.getString(cursor.getPosition());
    }


   //Methode finale pour recuperer la description d'un point
    public String getPointDescription(double lat,double lng){
        Cursor cursor=this.getWritableDatabase().query("pointInteret",new String[] {"description"},
                "latitude='"+lat+"' and longitude='"+lng+"'",null,
                null,null,null);

        cursor.moveToNext();
        System.out.println("Name-------------"+cursor.getString(cursor.getPosition()));
        return cursor.getString(cursor.getPosition());
    }

    //méthiode finale
    public ArrayList<Point> parcours(int id){
        ArrayList<Point> listPoint=new ArrayList<>();
        String strGetId="select id from parcours where name like ? ";
        String strGetParcours="Select latitude,longitude from parcours prc INNER JOIN point ptn "
                +"where  id_parcours=id";
        Cursor cursor=getWritableDatabase().rawQuery(strGetParcours,null);
        System.out.println("J'ai exectuté------------------------------------");
        cursor.moveToNext();
        System.out.println("La position est ---------------"+cursor.getPosition());
        int count=cursor.getCount();
        System.out.println("count est ---------------"+count);

        for (int i=0;i<count;i++){
            System.out.println("le point est:  "  +cursor.getDouble(0)+"--"+cursor.getDouble(1)+"----------------------------");
            listPoint.add(new Point(cursor.getDouble(0),cursor.getDouble(1)));
            cursor.moveToNext();
        }

        return listPoint;
    }


    public void deletePoint(double lat,double lng){

        String strDelete="delete from point where latitude='"+lat+"' and longitude='"+lng+"'";
        this.getWritableDatabase().execSQL(strDelete);
        System.out.println("j'ai supprimé le point");
        Toast deletePoint=Toast.makeText(getApplicationContext(),"Point suprimé", Toast.LENGTH_SHORT);
        deletePoint.show();

    }



    public void deletParcours(int id){
        String sqlDelete="delete from parcours where id= '"+id+"'";
        this.getWritableDatabase().execSQL(sqlDelete);
        System.out.println("j'ai supprimé le parcours");
        Toast deleteParcours=Toast.makeText(getApplicationContext(),"Parcours suprimé", Toast.LENGTH_SHORT);
        deleteParcours.show();

    }

}
