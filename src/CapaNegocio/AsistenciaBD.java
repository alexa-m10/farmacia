/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaNegocio;

import CapaConexion.Conexion;
import CapaDatos.Asistencia;
import CapaDatos.Turno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author labor
 */
public class AsistenciaBD {

    private Conexion mysql = new Conexion();
    private Connection cn = mysql.conectar();
    private String sql;

    public List<Asistencia> buscarAsistenciaUsuario(String uDni, String tienda, String fecha) {
        List<Asistencia> lista = new ArrayList<>();
        sql = "SELECT idasistencia,aFechaE,aHoraE,aHorasS,uDni,aTurno,aEstado,aTienda FROM asistencia "
                + "WHERE uDni=? AND aTienda=? AND aFechaE=? ";
        try {
            PreparedStatement pst = cn.prepareStatement(sql);

            pst.setString(1, uDni);
            pst.setString(2, tienda);
            pst.setString(3, fecha);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {

                Asistencia oaAsistencia = new Asistencia();

                oaAsistencia.setIdasistencia(rs.getInt(1));
                oaAsistencia.setaFechaE(rs.getString(2));
                oaAsistencia.setaHoraE(rs.getString(3));
                oaAsistencia.setaHorasS(rs.getString(4));
                oaAsistencia.setuDni(rs.getString(5));
                oaAsistencia.setaTurno(rs.getString(6));
                oaAsistencia.setaEstado(rs.getString(7));
                oaAsistencia.setaTienda(rs.getString(8));

                lista.add(oaAsistencia);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error al buscar asistencia", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return lista;
    }

    public int registrarAsistencia(Asistencia a) {

        int idasistencia = 0;
        sql = "INSERT INTO asistencia (idasistencia,aFechaE,aHoraE,aHorasS,uDni,aTurno,aEstado,aTienda) "
                + "VALUES(0,?,?,?,?,?,?,?)";

        try {

            PreparedStatement pst = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, a.getaFechaE());
            pst.setString(2, a.getaHoraE());
            pst.setString(3, a.getaHorasS());
            pst.setString(4, a.getuDni());
            pst.setString(5, a.getaTurno());
            pst.setString(6, a.getaEstado());
            pst.setString(7, a.getaTienda());

            pst.executeUpdate();
            ResultSet resultado = (ResultSet) pst.getGeneratedKeys();
            if (resultado.next()) {

                idasistencia = resultado.getInt(1);

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e, "Problemas al registrar asistencia BD..", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        return idasistencia;
    }

}
