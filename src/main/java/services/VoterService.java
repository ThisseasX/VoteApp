package services;

import models.Candidate;
import models.Voter;
import utils.DBUtils;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VoterService {

    public static boolean register(Voter v, String password) {
        String sql = "INSERT INTO voters VALUES (?,?,?,?)";

        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, v.getAfm());
            ps.setString(2, v.getName());
            ps.setString(3, v.getSurname());
            ps.setString(4, password);
            return ps.executeUpdate() > 0;

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean login(Voter v, String password) {
        String sql = "SELECT v_name, v_surname FROM voters WHERE v_afm=? AND BINARY v_password=?";

        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, v.getAfm());
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                v.setName(rs.getString(1));
                v.setSurname(rs.getString(2));
                return true;
            }

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
        return false;
    }

    @SuppressWarnings("WeakerAccess")
    public static boolean canVote(String afm) {
        String sql = "SELECT count(*) FROM votes WHERE v_afm = ?";

        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, afm);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) < 3;
            }

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void vote(String v_afm, String c_afm, int vote) {
        if (canVote(v_afm)) {
            String sql = "INSERT INTO votes VALUES(?,?,?,?)";

            try (Connection con = DBUtils.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String date = now.format(formatter);

                ps.setString(1, v_afm);
                ps.setString(2, c_afm);
                ps.setString(3, date);
                ps.setInt(4, vote);
                ps.executeUpdate();

            } catch (SQLException | NamingException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Candidate> getVoteHistory(Voter v) {
        //language=MySQL
        String sql = "" +
                "SELECT " + CandidateService.SQL_CANDIDATE + ", vote " +
                "FROM candidates " +
                CandidateService.SQL_JOIN_VOTES + " " +
                "WHERE votes.v_afm = ?";

        return CandidateService.getCandidateList(v, sql);
    }
}
