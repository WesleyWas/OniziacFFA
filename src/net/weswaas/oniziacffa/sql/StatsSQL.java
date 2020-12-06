package net.weswaas.oniziacffa.sql;

import org.bukkit.entity.Player;

import java.sql.*;

public class StatsSQL {

    private String url_base, host, name, username, password, table;
    private Connection connection;

    public StatsSQL(String url_base, String host, String name, String username, String password, String table){
    this.url_base = url_base;
    this.host = host;
    this.name = name;
    this.username = username;
    this.password = password;
    this.table = table;
    }

    public void connection(){
        if(!isConnected()){
            try{
                connection = DriverManager.getConnection(url_base + host + "/" + name, username, password);

                PreparedStatement sts = connection.prepareStatement("CREATE TABLE IF NOT EXISTS ffa ()");

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void deconnection(){
        if(isConnected()){
            try{
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    private boolean isConnected(){
		try{
        if((connection == null) || connection.isClosed() || (!connection.isValid(5))){
            return false;
        }else{
            return true;
        }
    }catch (SQLException e){
        e.printStackTrace();
    }
		return false;
}

    private Connection getConnection(){
        return connection;
    }

    public void createAccount(Player player){
        try{

            PreparedStatement sts = getConnection().prepareStatement("SELECT kills FROM "+ table + " WHERE uuid = ?");
            sts.setString(1, player.getUniqueId().toString());
            ResultSet rs = sts.executeQuery();
            if(!rs.next()){
                sts.close();
                PreparedStatement sts2 = getConnection().prepareStatement("INSERT INTO " + table + " (name, uuid, kills, deaths, kdr, ks) VALUES (?, ?, ?, ?, ?, ?)");
                sts2.setString(1, player.getDisplayName());
                sts2.setString(2, player.getUniqueId().toString());
                sts2.setInt(3, 0);
                sts2.setInt(4, 0);
                sts2.setString(5, "0");
                sts2.setInt(6, 0);
                sts2.executeUpdate();
                sts2.close();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void setKills(String uuid, Integer amount){
        try{

            PreparedStatement sts = getConnection().prepareStatement("UPDATE " + table + " SET kills = ? WHERE uuid = ?");
            sts.setInt(1, amount);
            sts.setString(2, uuid.toString());
            sts.executeUpdate();
            sts.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void setDeaths(String uuid, Integer amount){
        try{

            PreparedStatement sts = getConnection().prepareStatement("UPDATE " + table + " SET deaths = ? WHERE uuid = ?");
            sts.setInt(1, amount);
            sts.setString(2, uuid.toString());
            sts.executeUpdate();
            sts.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void setKDR(String uuid, String amount){
        try{

            PreparedStatement sts = getConnection().prepareStatement("UPDATE " + table + " SET kdr = ? WHERE uuid = ?");
            sts.setString(1, amount);
            sts.setString(2, uuid.toString());
            sts.executeUpdate();
            sts.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void setKS(String uuid, Integer amount){
        try{

            PreparedStatement sts = getConnection().prepareStatement("UPDATE " + table + " SET ks = ? WHERE uuid = ?");
            sts.setDouble(1, amount);
            sts.setString(2, uuid.toString());
            sts.executeUpdate();
            sts.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Integer getKills(String uuid){
        int kills = 0;

        try{
            PreparedStatement sts = getConnection().prepareStatement("SELECT kills FROM " + table + " WHERE uuid = ?");
            sts.setString(1, uuid);
            ResultSet rs = sts.executeQuery();
            if(!rs.next()){
                return kills;
            }
            kills = rs.getInt("kills");
            sts.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return kills;
    }

    public Integer getDeaths(String uuid){
        int deaths = 0;

        try{
            PreparedStatement sts = getConnection().prepareStatement("SELECT deaths FROM " + table + " WHERE uuid = ?");
            sts.setString(1, uuid);
            ResultSet rs = sts.executeQuery();
            if(!rs.next()){
                return deaths;
            }
            deaths = rs.getInt("deaths");
            sts.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return deaths;
    }

    public String getKDR(String uuid){
        String kdr = "0";

        try{
            PreparedStatement sts = getConnection().prepareStatement("SELECT kdr FROM " + table + " WHERE uuid = ?");
            sts.setString(1, uuid);
            ResultSet rs = sts.executeQuery();
            if(!rs.next()){
                return kdr;
            }
            kdr = rs.getString("kdr");
            sts.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return kdr;
    }

    public Integer getKillstreak(String uuid){
        int ks = 0;

        try{
            PreparedStatement sts = getConnection().prepareStatement("SELECT ks FROM " + table + " WHERE uuid = ?");
            sts.setString(1, uuid);
            ResultSet rs = sts.executeQuery();
            if(!rs.next()){
                return ks;
            }
            ks = rs.getInt("ks");
            sts.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ks;
    }

}