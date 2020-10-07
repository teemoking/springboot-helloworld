package springhtwo.tee;

import lombok.extern.slf4j.Slf4j;
import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.ConnectionProxy;

import java.util.Properties;

@Slf4j
public class ConnectionLogFilter extends FilterEventAdapter{

    @Override
    public void connection_connectBefore(FilterChain chain, Properties info){
        log.info("before connect");
    }
    @Override
    public void connection_connectAfter(ConnectionProxy connection){
        log.info("after connect");
    }
}
