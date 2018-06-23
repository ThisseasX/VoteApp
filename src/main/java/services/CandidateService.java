package services;

import models.Candidate;
import models.Voter;
import utils.DBUtils;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class CandidateService {

    static final String SQL_CANDIDATE = "candidates.c_afm, c_name, c_surname";
    static final String SQL_JOIN_VOTES = "JOIN votes ON candidates.c_afm = votes.c_afm";

    public static List<Candidate> getAvailableCandidates(Voter v) {

        //language=MySQL
        String sqlVotedCandidates = "" +
                "SELECT c_afm " +
                "FROM votes " +
                "WHERE v_afm = ?";

        //language=MySQL
        String sqlAvailableCandidates = "" +
                "SELECT " + SQL_CANDIDATE + " " +
                "FROM candidates " +
                "WHERE c_afm NOT IN " +
                "(" + sqlVotedCandidates + ")";

        return getCandidateList(v, sqlAvailableCandidates);
    }

    public static List<Candidate> getRankings() {
        //language=MySQL
        String sql = "" +
                "SELECT " + SQL_CANDIDATE + ", sum(vote) sum " +
                "FROM candidates " +
                SQL_JOIN_VOTES + " " +
                "GROUP BY " + SQL_CANDIDATE + " " +
                "ORDER BY sum DESC";

        return getCandidateList(null, sql);
    }

    static List<Candidate> getCandidateList(Voter v, String sql) {
        List<Candidate> list = new ArrayList<>();

        try (Connection con = DBUtils.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (v != null) ps.setString(1, v.getAfm());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Candidate c = new Candidate(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3)
                );
                if (rs.getMetaData().getColumnCount() == 4) {
                    c.setVotes(rs.getInt(4));
                }
                list.add(c);
            }

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
        return list;
    }
}
