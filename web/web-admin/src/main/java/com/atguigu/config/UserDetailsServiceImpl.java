package com.atguigu.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import com.github.pagehelper.util.StringUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Reference
    private AdminService adminService;
    @Reference
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Admin admin = adminService.getByUsername(username);
        if(admin == null)
        {
            throw new UsernameNotFoundException("用户名不存在!");
        }
        List<String> codes = permissionService.findCodePermissionListByAdminId(admin.getId());
        List<GrantedAuthority> grantedAuthorityList = new ArrayList();
        for (String code : codes) {
            if(code == null || code.equals(""))
                continue;
            grantedAuthorityList.add(new SimpleGrantedAuthority(code));
        }
        return new User(username,admin.getPassword(), grantedAuthorityList);
    }
}
