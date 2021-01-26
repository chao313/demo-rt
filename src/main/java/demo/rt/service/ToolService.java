package demo.rt.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Properties;

@Slf4j
@Service
public class ToolService {
    /**
     * 系统信息
     */
    public JSONObject getSystemEnvInfo() throws UnknownHostException {
        Properties props = System.getProperties();
        Map<String, String> map = System.getenv();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("server.user.name", map.get("USERNAME")); //用户名
        jsonObject.put("server.computer.name", map.get("COMPUTERNAME")); //计算机名
        jsonObject.put("server.computer.domain", map.get("USERDOMAIN")); //计算机域名
        InetAddress inetAddress = InetAddress.getLocalHost();
        jsonObject.put("server.ip", inetAddress.getHostAddress()); //本机ip
        jsonObject.put("server.host.name", inetAddress.getHostName()); //本机主机名
        jsonObject.put("server.user.home", props.getProperty("user.home")); //用户的主目录
        jsonObject.put("server.user.dir", props.getProperty("user.dir")); //用户的当前工作目录
        return jsonObject;
    }

}
