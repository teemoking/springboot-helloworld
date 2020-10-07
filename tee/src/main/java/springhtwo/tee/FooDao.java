package springhtwo.tee;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.print.DocFlavor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Repository
public class FooDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;

    public void insertData(){
        Arrays.asList("a","b").forEach(bar ->{
            jdbcTemplate.update("INSERT INTO FOO (BAR) VALUES (?)",bar);
        });
        HashMap<String,String> row = new HashMap<>();
        row.put("BAR","d");
        Number id = simpleJdbcInsert.executeAndReturnKey(row);
        log.info("ID of d: {}",id.longValue());
    }
    public void listData(){
        log.info("count: {}",jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO",Long.class));
        List<String> list = jdbcTemplate.queryForList("SELECT BAR FROM FOO", String.class);
        list.forEach(s -> log.info("BAR {}",s));

        List<Foo> foolist = jdbcTemplate.query("SELECT * FROM FOO",new RowMapper<Foo>(){
            @Override
            public Foo mapRow(ResultSet rs, int rowNum) throws SQLException{
                return Foo.builder()
                        .id(rs.getLong(1))
                        .bar(rs.getString(2))
                        .build();
            }
        });
        foolist.forEach(f -> log.info("Foo: {}",f));

    }








}
