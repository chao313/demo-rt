package demo.rt.service.dao;

import demo.rt.service.vo.Tb4828;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 股东相关
 */
@Slf4j
@Repository
public class DAO4828 {

    @Autowired
    @Qualifier("tempJdbcTemplate")
    private JdbcTemplate jdbcTemplate;


    /**
     * 根据F1 获取  4828
     */
    public List<Tb4828> queryByF1(String F1_4828) {
        List<Tb4828> tb4828s = new ArrayList<>();
        String sql = "SELECT  OB_OBJECT_ID, " +
                "F1_4828,F2_4828,F3_4828,F4_4828,F5_4828,F6_4828,F7_4828 FROM WIND.TB_OBJECT_4828 WHERE F1_4828  = '" + F1_4828 + "'";
        try {
            jdbcTemplate.query(sql, rs -> {
                do {
                    String F1_4828_DB = rs.getString("F1_4828");
                    String F2_4828 = rs.getNString("F2_4828");
                    String F3_4828 = rs.getString("F3_4828");
                    String F4_4828 = rs.getString("F4_4828");
                    String F5_4828 = rs.getString("F5_4828");
                    String F6_4828 = rs.getString("F6_4828");
                    String F7_4828 = rs.getString("F7_4828");

                    Tb4828 tb4828 = new Tb4828();
                    tb4828.setF1_4828(F1_4828_DB);
                    tb4828.setF2_4828(F2_4828);
                    tb4828.setF3_4828(F3_4828);
                    tb4828.setF4_4828(F4_4828);
                    tb4828.setF5_4828(F5_4828);
                    tb4828.setF6_4828(F6_4828);
                    tb4828.setF7_4828(F7_4828);

                    tb4828s.add(tb4828);
                } while (rs.next());
            });

        } catch (Exception e) {
            e.printStackTrace();
            log.error("sql:{}", sql);

        }
        return tb4828s;
    }


    /**
     * 获取  4828
     */
    public List<Tb4828> queryAll() {
        List<Tb4828> tb4828s = new ArrayList<>();
        String sql = "SELECT  OB_OBJECT_ID, " +
                "F1_4828,F2_4828,F3_4828,F4_4828,F5_4828,F6_4828,F7_4828 FROM WIND.TB_OBJECT_4828";
        try {
            jdbcTemplate.query(sql, rs -> {
                do {
                    String F1_4828_DB = rs.getString("F1_4828");
                    String F2_4828 = rs.getNString("F2_4828");
                    String F3_4828 = rs.getString("F3_4828");
                    String F4_4828 = rs.getString("F4_4828");
                    String F5_4828 = rs.getString("F5_4828");
                    String F6_4828 = rs.getString("F6_4828");
                    String F7_4828 = rs.getString("F7_4828");

                    Tb4828 tb4828 = new Tb4828();
                    tb4828.setF1_4828(F1_4828_DB);
                    tb4828.setF2_4828(F2_4828);
                    tb4828.setF3_4828(F3_4828);
                    tb4828.setF4_4828(F4_4828);
                    tb4828.setF5_4828(F5_4828);
                    tb4828.setF6_4828(F6_4828);
                    tb4828.setF7_4828(F7_4828);

                    tb4828s.add(tb4828);
                } while (rs.next());
            });

        } catch (Exception e) {
            e.printStackTrace();
            log.error("sql:{}", sql);

        }
        return tb4828s;
    }

}
