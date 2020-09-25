package com.example.randonner.externelDataBase;

public class Configuration {

    //6 mai


    //address of our scripts

    public static  final  String IP="10.30.13.209";
    public static  final  String URL_ADD_USER="http://"+IP+"/randonner/addUser.php";
    public static  final  String LOGIN="http://"+IP+"/randonner/login.php";
    public static  final  String URL_UPLOAD_PROFILE_PICTURE="http://"+IP+"/randonner/uploadProfileImage.php";
    public static  final  String URL_ADD_POINT_INTERET="http://"+IP+"/randonner/addPointInteret.php";
    public static  final  String URL_ADD_POINT_INTERET_PICTURE="http://"+IP+"/randonner/addPointInteretPicture.php";
    public static  final  String URL_ADD_POINT="http://"+IP+"/randonner/addPoint.php";
    public static  final  String URL_ADD_PARCOURS="http://"+IP+"/randonner/addParcours.php";
    public static  final  String UPDATE="http://"+IP+"/randonner/updateUser.php";
    public static  final  String DELETE_USER="http://"+IP+"/randonner/deleteUtilisateur.php";
    public static  final  String DELETE_PARCOURS="";
    public static  final  String DELETE_POINT_INTERET="";
    public static  final  String GET_USER="";
    public static  final  String ADD_POINT_INTERET_PICTURE="http://"+IP+"/randonner/addPointInteretPicture.php";
    public static  final  String GET_PARCOURS="http://"+IP+"/randonner/getParcours.php";
    public static  final  String GET_POINTS="http://"+IP+"/randonner/getPoints.php";
    public static  final  String GET_POINTS_INTERETS="http://"+IP+"/randonner/getPointsInterets";
    public static  final  String GET_POINTS_INTERETS_PICTURES="http://"+IP+"/randonner/getPointInteretPictures.php";
    public static  final  String PATH_PROFILE_PICTURE="http://"+IP+"/randonner/profile_image/";




    //keys that will be used to send the request to php scripts


    public static  final  String KEY_USER_ID="id";
    public static  final  String KEY_USER_NOM="nom";
    public static  final  String KEY_USER_PRENOM="prenom";
    public static  final  String KEY_USER_MAIL="mail";
    public static  final  String KEY_USER_TEL="tel";
    public static  final  String KEY_USER_PASSWORD="motDePasse";
    public static  final  String KEY_USER_BIRTHDATE="dateDeNaissance";
    public static  final  String KEY_USER_SRC_IMAGE="src_image";


    //JSON Tags
    public static  final  String TAG_JSON_SUCCESS="success";
    public static  final  String TAG_JSON_NUM_ROWS="num_rows";
    public static  final  String TAG_JSON_ARRAY="result";
    public static  final  String TAG_USER_ID="id";
    public static  final  String TAG_POINT_LATITUDE="latitude";
    public static  final  String TAG_POINT_LONGITUDE="longitude";
    public static  final  String TAG_POINT_POSITION="position";
    public static  final  String TAG_PARCOURS_ID="id_parcours";
    public static  final  String TAG_USER_NOM="nom";
    public static  final  String TAG_USER_PRENOM="prenom";
    public static  final  String TAG_USER_MAIL="mail";
    public static  final  String TAG_USER_TEL="tel";
    public static  final  String TAG_USER_PASSWORD="motDePasse";
    public static  final  String TAG_USER_BIRTHDATE="dateDeNaissance";


    //PARCOURS

    public static  final  String PARCOURS_ID="id_parcours";
    public static  final  String PARCOURS_NOM="nom_parcours";
    public static  final  String PARCOURS_DATE="date_parcours";
    public static  final  String PARCOURS_DESCRIPTION="description_parcours";


    public static  final  String USER_ID="id_utilisateur";
    public static  final  String LATITUDE="latitude";
    public static  final  String LONGITUDE="longitude";
    public static  final  String POSITION="position";



    public static  final  String POINT_INTERET_NOM="nom_point_interet";
    public static  final  String POINT_INTERET_DESCRIPTION="description_point_interet";



}
