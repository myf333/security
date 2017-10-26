package com.myf.security;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import java.util.*;

/**
 * Created by myf on 2017/10/26.
 */
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource{
    private String userRoles;
    private Map<String,Collection<ConfigAttribute>> resourceMap;

    public CustomSecurityMetadataSource(String userRoles) {
        this.userRoles = userRoles;
        resourceMap = loadResourceMatchAuthority();
    }

    private Map<String,Collection<ConfigAttribute>> loadResourceMatchAuthority(){
        Map<String,Collection<ConfigAttribute>> map = new HashMap<>(16);
        if(userRoles!=null&&!userRoles.isEmpty()){
            String[] resources = userRoles.split(";");
            for (String resource : resources){
                String[] urls = resource.split("=");
                String[] roles = urls[1].split(",");
                Collection<ConfigAttribute> list = new ArrayList<>();
                for (String role : roles){
                    ConfigAttribute config = new SecurityConfig(role.trim());
                    list.add(config);
                }
                map.put(urls[0].trim(),list);
            }
        }
        return map;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String url = ((FilterInvocation)object).getRequestUrl();
        if(resourceMap == null){
            resourceMap = loadResourceMatchAuthority();
        }
        AntPathMatcher pathMatcher = new AntPathMatcher();
        Iterator<String> iterable = resourceMap.keySet().iterator();
        while (iterable.hasNext()){
            String resource = iterable.next();
            if(pathMatcher.match(resource,url)){
                return resourceMap.get(resource);
            }
        }
        return resourceMap.get(url);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> set = new HashSet<>();
        for(Map.Entry<String,Collection<ConfigAttribute>> entry:resourceMap.entrySet()){
            set.addAll(entry.getValue());
        }
        return set;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true ;
    }
}
