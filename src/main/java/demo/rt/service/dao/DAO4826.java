//package demo.rt.service.dao;
//
//import demo.rt.service.vo.Tb4826;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 股东相关
// */
//@Slf4j
//@Repository
//public class DAO4826 {
//
//    @Resource
//    private JdbcTemplate jdbcTemplate;
//
//
//    /**
//     * 根据F1 获取  4826
//     */
//    public List<Tb4826> queryByF1(String F1_4826) {
//        List<Tb4826> tb4826s = new ArrayList<>();
//        String sql = "SELECT  OB_OBJECT_ID, " +
//                "F1_4826,F2_4826,F3_4826 FROM WIND.TB_OBJECT_4826 WHERE F1_4826  = '" + F1_4826 + "'";
//        try {
//            jdbcTemplate.query(sql, rs -> {
//                do {
//                    String F1_4826_DB = rs.getString("F1_4826");
//                    String F2_4826 = rs.getNString("F2_4826");
//                    String F3_4826 = rs.getString("F3_4826");
//
//                    Tb4826 tb4826 = new Tb4826();
//                    tb4826.setF1_4826(F1_4826_DB);
//                    tb4826.setF2_4826(F2_4826);
//                    tb4826.setF3_4826(F3_4826);
//
//                    tb4826s.add(tb4826);
//                } while (rs.next());
//            });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("sql:{}", sql);
//
//        }
//        return tb4826s;
//    }
//
//
//    /**
//     * 获取  4826
//     */
//    public List<Tb4826> queryAll() {
//        List<Tb4826> tb4826s = new ArrayList<>();
//        String sql = "SELECT  OB_OBJECT_ID, F1_4826,F2_4826,F3_4826 FROM WIND.TB_OBJECT_4826";
//        try {
//            jdbcTemplate.query(sql, rs -> {
//                do {
//                    String F1_4826_DB = rs.getString("F1_4826");
//                    String F2_4826 = rs.getNString("F2_4826");
//                    String F3_4826 = rs.getString("F3_4826");
//
//                    Tb4826 tb4826 = new Tb4826();
//                    tb4826.setF1_4826(F1_4826_DB);
//                    tb4826.setF2_4826(F2_4826);
//                    tb4826.setF3_4826(F3_4826);
//                    tb4826s.add(tb4826);
//                } while (rs.next());
//            });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("sql:{}", sql);
//
//        }
//        return tb4826s;
//    }
//
//}
