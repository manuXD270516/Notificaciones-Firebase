package com.example.usuario.notificacionfirebase.WEBSERVICES;

/**
 * Created by USUARIO on 23/07/2017.
 */

public class Constantes {

    public static final int CODIGO_DETALLE = 100;

    /**
     * Transición Detalle -> Actualización
     */
    public static final int CODIGO_ACTUALIZACION = 101;
    /**
     * Puerto que utilizas para la conexión.
     * Dejalo en blanco si no has configurado esta carácteristica.
     */
    private static final String PUERTO_HOST = ":8080";//":8080";//:63343";

    /**
     * Dirección IP de genymotion o AVD
     */

    private static final String IP ="192.168.43.168";////www.pruebaws.hol.es";///"192.168.0.29" ;//"192.168.0.166";//"10.0.3.2";

    /**
     * URLs del Web Service
     */

    /*public static final String GET_CATEOGORIAS = "http://" + IP + PUERTO_HOST + "/I%20Wish/obtener_metas.php";
    public static final String GET_BY_ID = "http://" + IP + PUERTO_HOST + "/I%20Wish/obtener_meta_por_id.php";
    public static final String UPDATE = "http://" + IP + PUERTO_HOST + "/I%20Wish/actualizar_meta.php";
    public static final String DELETE = "http://" + IP + PUERTO_HOST + "/I%20Wish/borrar_meta.php";
    public static final String INSERT = "http://" + IP + PUERTO_HOST + "/I%20Wish/insertar_meta.php";*/

    public static final String URL_PETICION_NOTIFICACION = "http://" + IP + PUERTO_HOST + "/Notificacion_Firebase/push_notification.php";
    public static final String URL_VISITAS="http://" + IP + PUERTO_HOST + "/WebServices_Maptu/Controller_User.php";
    public static final String HTTP_NEGOCIO = "http://" + IP + PUERTO_HOST + "/WebServices_Maptu/Controller_ObtenerNegocio.php";
    public static final String URL_INFORMACION="http://" + IP + PUERTO_HOST + "/Webservices_ContaduriaCima/Controller_Informacion.php";

    public static final String GET_BY_ID = "http://" + IP + PUERTO_HOST + "/WebServices_IWish/obtener_meta_por_id.php";
    public static final String UPDATE = "http://" + IP + PUERTO_HOST + "/WebServices_IWish/actualizar_meta.php";
    public static final String DELETE = "http://" + IP + PUERTO_HOST + "/WebServices_IWish/borrar_meta.php";
    public static final String INSERT = "http://" + IP + PUERTO_HOST + "/WebServices_IWish/insertar_meta.php";


    /**
     * Clave para el valor extra que representa al identificador de una meta
     */
    public static final String EXTRA_ID = "IDEXTRA";

}
