package co.edu.uniquindio.programacion3.subastaquindio.utils;

import co.edu.uniquindio.programacion3.subastaquindio.exceptions.UsuarioException;
import co.edu.uniquindio.programacion3.subastaquindio.model.*;
import co.edu.uniquindio.programacion3.subastaquindio.model.Producto;
import co.edu.uniquindio.programacion3.subastaquindio.model.SubastaQuindio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static co.edu.uniquindio.programacion3.subastaquindio.utils.ArchivoUtil.cargarFechaSistemaFormatoRespaldo;

public class Persistencia {

    static String fechaRespaldo = cargarFechaSistemaFormatoRespaldo();

    public static final String RUTA_ARCHIVO_PRODUCTOS = "src/main/resources/persistencia/archivos/archivoProductos.txt";
    public static final String RUTA_ARCHIVO_PRODUCTOS_RESPALDO_XML = "src/main/resources/persistencia/respaldo/archivoProductos" + fechaRespaldo +".xml";
    public static final String RUTA_ARCHIVO_USUARIOS = "src/main/resources/persistencia/archivos/archivoUsuarios.txt";
    public static final String RUTA_ARCHIVO_LOG = "src/main/resources/persistencia/log/SubastaLog.txt";
    public static final String RUTA_ARCHIVO_RESPALDO_XML = "src/main/resources/persistencia/respaldo/subastaRespaldo" + fechaRespaldo +".xml";
    public static final String RUTA_ARCHIVO_OBJETOS = "src/main/resources/persistencia/archivos/archivoObjetos.txt";
    public static final String RUTA_ARCHIVO_MODELO_SUBASTA_BINARIO = "src/main/resources/persistencia/model.dat";
    public static final String RUTA_ARCHIVO_MODELO_SUBASTA_XML = "src/main/resources/persistencia/model.xml";
//	C:\td\persistencia


    public static void cargarDatosArchivos(SubastaQuindio subasta) throws FileNotFoundException, IOException {
        //cargar archivo de producto
        ArrayList<Producto> productosCargados = cargarProductos();
        if (productosCargados.size() > 0)
            subasta.getListaProductos().addAll(productosCargados);

        //cargar archivos compradores

        //cargar archivo transcciones

        //cargar archivo empleados

        //cargar archivo prestamo

    }

    /**
     * Guarda en un archivo de texto todos la información de las personas almacenadas en el ArrayList
     *
     * @param
     * @param
     * @throws IOException
     */
    public static void guardarProducto(ArrayList<Producto> listaProductos) throws IOException {
        // TODO Auto-generated method stub
        String contenido = "";
        for (Producto producto : listaProductos) {
            contenido += producto.getCodigoUnico() + "@@" + producto.getNombreProducto() + "@@" + producto.getDescripcion() + "@@" + producto.getNombreAnunciante()
                    + "@@" + producto.getTipoProducto() + "@@" + producto.getFechaPublicacion() + "@@" + producto.getFechaFinPublicacion() + "@@" + producto.getValorInicial() + "\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_PRODUCTOS, contenido, false);
    }


//	----------------------LOADS------------------------

    /**
     * @param
     * @param
     * @return un Arraylist de personas con los datos obtenidos del archivo de texto indicado
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static ArrayList<Producto> cargarProductos() throws FileNotFoundException, IOException {
        ArrayList<Producto> productos = new ArrayList<Producto>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_PRODUCTOS);
        String linea = "";
        for (int i = 0; i < contenido.size(); i++) {
            linea = contenido.get(i);//juan@@arias@@125454@@Armenia@@uni1@@@12454@@125444
            Producto producto = new Producto();
            producto.setCodigoUnico(linea.split("@@")[0]);
            producto.setNombreProducto(linea.split("@@")[1]);
            producto.setDescripcion(linea.split("@@")[2]);
            producto.setNombreAnunciante(linea.split("@@")[3]);
            producto.setTipoProducto(linea.split("@@")[4]);
            producto.setFechaPublicacion(linea.split("@@")[5]);
            producto.setFechaFinPublicacion(linea.split("@@")[6]);
            producto.setValorInicial(Double.parseDouble(linea.split("@@")[7]));
            productos.add(producto);
        }
        return productos;
    }


    public static void guardaRegistroLog(String mensajeLog, int nivel, String accion) {
        ArchivoUtil.guardarRegistroLog(mensajeLog, nivel, accion, RUTA_ARCHIVO_LOG);
    }


    public static boolean iniciarSesion(String usuario, String contrasenia) throws FileNotFoundException, IOException, UsuarioException {

        if (validarUsuario(usuario, contrasenia)) {
            return true;
        } else {
            throw new UsuarioException("Usuario no existe");
        }

    }

    private static boolean validarUsuario(String usuario, String contrasenia) throws FileNotFoundException, IOException {
        ArrayList<Usuario> usuarios = Persistencia.cargarUsuarios(RUTA_ARCHIVO_USUARIOS);

        for (int indiceUsuario = 0; indiceUsuario < usuarios.size(); indiceUsuario++) {
            Usuario usuarioAux = usuarios.get(indiceUsuario);
            if (usuarioAux.getUsuario().equalsIgnoreCase(usuario) && usuarioAux.getContrasenia().equalsIgnoreCase(contrasenia)) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Usuario> cargarUsuarios(String ruta) throws FileNotFoundException, IOException {
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

        ArrayList<String> contenido = ArchivoUtil.leerArchivo(ruta);
        String linea = "";

        for (int i = 0; i < contenido.size(); i++) {
            linea = contenido.get(i);

            Usuario usuario = new Usuario();
            usuario.setUsuario(linea.split(",")[0]);
            usuario.setContrasenia(linea.split(",")[1]);

            usuarios.add(usuario);
        }
        return usuarios;
    }


//	----------------------SAVES------------------------

    /**
     * Guarda en un archivo de texto todos la información de las personas almacenadas en el ArrayList
     *
     * @param
     * @param ruta
     * @throws IOException
     */

    public static void guardarObjetos(ArrayList<Anunciante> listaAnunciantes, String ruta) throws IOException {
        String contenido = "";

        for (Anunciante  anuncianteAux : listaAnunciantes) {
            contenido += anuncianteAux.getNombre() + "," + anuncianteAux.getApellido() + "," + anuncianteAux.getCedula() + anuncianteAux.getDireccion()
                    + "," + anuncianteAux.getCorreo() + "," + anuncianteAux.getFechaNacimiento() + "," + anuncianteAux.getTelefono() + "\n";
        }
        ArchivoUtil.guardarArchivo(ruta, contenido, true);
    }


    //------------------------------------SERIALIZACIÓN  y XML


    public static SubastaQuindio cargarRecursoSubastaBinario() {

        SubastaQuindio subasta = null;

        try {
            subasta = (SubastaQuindio) ArchivoUtil.cargarRecursoSerializado(RUTA_ARCHIVO_MODELO_SUBASTA_BINARIO);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return subasta;
    }

    public static void guardarRecursoSubastaBinario(SubastaQuindio subasta) {
        try {
            ArchivoUtil.salvarRecursoSerializado(RUTA_ARCHIVO_MODELO_SUBASTA_BINARIO, subasta);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static SubastaQuindio cargarRecursoSubastaXML() {

        SubastaQuindio subasta = null;

        try {
            subasta = (SubastaQuindio) ArchivoUtil.cargarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_SUBASTA_XML);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return subasta;

    }


    public static void guardarRecursoSubastaXML(SubastaQuindio subasta) {

        try {
            ArchivoUtil.salvarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_SUBASTA_XML, subasta);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void copiarArchivoRespaldoXml(){
        ArchivoUtil.copiarArchivoRespaldo(RUTA_ARCHIVO_MODELO_SUBASTA_XML,RUTA_ARCHIVO_RESPALDO_XML);

    }

}