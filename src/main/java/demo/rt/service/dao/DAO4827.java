package demo.rt.service.dao;

import demo.rt.service.vo.Tb4827;
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
public class DAO4827 {

    @Autowired
    @Qualifier("tempJdbcTemplate")
    private JdbcTemplate jdbcTemplate;


    /**
     * 根据F1 获取  4827
     */
    public List<Tb4827> queryByF1(String F1_4827) {
        List<Tb4827> tb4827s = new ArrayList<>();
        String sql = "SELECT  OB_OBJECT_ID, " +
                "F1_4827,F2_4827,F3_4827,F4_4827 FROM WIND.TB_OBJECT_4827 WHERE F1_4827  = '" + F1_4827 + "'";
        try {
            jdbcTemplate.query(sql, rs -> {
                do {
                    String F1_4827_DB = rs.getString("F1_4827");
                    String F2_4827 = rs.getNString("F2_4827");
                    String F3_4827 = rs.getString("F3_4827");
                    String F4_4827 = rs.getString("F4_4827");

                    Tb4827 tb4827 = new Tb4827();
                    tb4827.setF1_4827(F1_4827_DB);
                    tb4827.setF2_4827(F2_4827);
                    tb4827.setF3_4827(F3_4827);
                    tb4827.setF4_4827(F4_4827);

                    tb4827s.add(tb4827);
                } while (rs.next());
            });

        } catch (Exception e) {
            e.printStackTrace();
            log.error("sql:{}", sql);

        }
        return tb4827s;
    }


    /**
     * 获取  4827
     */
    public List<Tb4827> queryAll() {
        List<Tb4827> tb4827s = new ArrayList<>();
        String sql = "SELECT  OB_OBJECT_ID, F1_4827,F2_4827,F3_4827,F4_4827 FROM WIND.TB_OBJECT_4827";
        try {
            jdbcTemplate.query(sql, rs -> {
                do {
                    String F1_4827_DB = rs.getString("F1_4827");
                    String F2_4827 = rs.getNString("F2_4827");
                    String F3_4827 = rs.getString("F3_4827");
                    String F4_4827 = rs.getString("F4_4827");


                    Tb4827 tb4827 = new Tb4827();
                    tb4827.setF1_4827(F1_4827_DB);
                    tb4827.setF2_4827(F2_4827);
                    tb4827.setF3_4827(F3_4827);
                    tb4827.setF4_4827(F4_4827);
                    tb4827s.add(tb4827);
                } while (rs.next());
            });

        } catch (Exception e) {
            e.printStackTrace();
            log.error("sql:{}", sql);

        }
        return tb4827s;
    }

}
