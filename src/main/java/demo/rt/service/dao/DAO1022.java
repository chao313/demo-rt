//package demo.rt.service.dao;
//
//import demo.rt.service.vo.Tb1022;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 股东相关
// */
//@Slf4j
//@Repository
//public class DAO1022 {
//
//    @Resource
//    private JdbcTemplate jdbcTemplate;
//
//
//    /**
//     * 根据F1 获取  1022
//     */
//    public List<Tb1022> queryAll() {
//        List<Tb1022> tb1022s = new ArrayList<>();
//        String sql = "SELECT  NAME , CODE FROM WIND.TB_OBJECT_1022";
//        try {
//            jdbcTemplate.query(sql, rs -> {
//                do {
//                    String NAME = rs.getString("NAME");
//                    String CODE = rs.getNString("CODE");
//                    Tb1022 tb1022 = new Tb1022();
//                    tb1022.setName(NAME);
//                    tb1022.setCode(CODE);
//                    tb1022s.add(tb1022);
//                } while (rs.next());
//            });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("sql:{}", sql);
//        }
//        return tb1022s;
//    }
//
//}
