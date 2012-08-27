/*
 * AeSOAAuthentificationProvider.java
 * 
 * Copyright (C) 2011
 * 
 * This file is part of Proyecto persistenceGeo
 * 
 * This software is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this library; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 * 
 * As a special exception, if you link this library with other files to produce
 * an executable, this library does not by itself cause the resulting executable
 * to be covered by the GNU General Public License. This exception does not
 * however invalidate any other reasons why the executable file might be covered
 * by the GNU General Public License.
 * 
 * Authors:: Alejandro Díaz Torres (mailto:adiaz@emergya.com)
 */
package com.emergya.persistenceGeo.security;

import java.util.Collection;
import java.util.HashSet;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.LdapAuthenticator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

import com.emergya.persistenceGeo.dto.UserDto;
import com.emergya.persistenceGeo.service.UserAdminService;

/**
 * Authentification provider for persistenceGeo
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 *
 */
public class DummyAuthentificationProvider 
	extends LdapAuthenticationProvider 
	implements AuthenticationProvider{

	@Resource
	private UserAdminService userAdminService;
	
	public static GrantedAuthority ADMIN_AUTH;
	public static GrantedAuthority ROLE_USER;
	private static final String ROLE_USER_GROUP="user";

    /**
     * Constructor del padre
     */
    public DummyAuthentificationProvider(LdapAuthenticator authenticator, LdapAuthoritiesPopulator authoritiesPopulator) {
        super(authenticator, authoritiesPopulator);
    }

    /**
     * Constructor del padre
     */
	public DummyAuthentificationProvider(LdapAuthenticator authenticator) {
		super(authenticator);
	}
	
	@Autowired
	public void init (String NAME_ADMIN_GROUP){
		ADMIN_AUTH = new SimpleGrantedAuthority(NAME_ADMIN_GROUP);
		ROLE_USER = new SimpleGrantedAuthority(ROLE_USER_GROUP);
	}

	/**
	 * Create los detalles del usuario
	 */
	protected Authentication createSuccessfulAuthentication(
			UsernamePasswordAuthenticationToken authentication, UserDetails user) {
        Object password =  authentication.getCredentials();
        
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(user, password,
                user.getAuthorities());
        
        result.setDetails(authentication.getDetails());

        return result;
	}

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		//autentica en UserDetailsImpl
		final UsernamePasswordAuthenticationToken userToken = (UsernamePasswordAuthenticationToken)authentication;
		
		UserDetails user = new UserDetailsImpl(authentication.getName(), (String) authentication.getCredentials());
		return createSuccessfulAuthentication(userToken, user);
	}
	
	private class UserDetailsImpl implements UserDetails{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String username;
		private String password;
		Collection<GrantedAuthority> userAuths = null;
		
		public UserDetailsImpl(String username, String password){
			this.username = username;
			this.password = password;
			UserDto usuario = userAdminService.obtenerUsuario(username, (String) password);
	        

			userAuths = new HashSet<GrantedAuthority>();
	        //Es valido
	        if(usuario != null 
	        		&& usuario.getValid() != null
	        		&& usuario.getValid()){
	    		userAuths.add(ROLE_USER);
	        	if (usuario.getAdmin()){
	        		//Add admin auth
	        		userAuths.add(ADMIN_AUTH);
	        	}
	        }
		}
		
		@Override
		public boolean isEnabled() {
			return userAuths.contains(ROLE_USER);
		}
		
		@Override
		public boolean isCredentialsNonExpired() {
			return isEnabled();
		}
		
		@Override
		public boolean isAccountNonLocked() {
			return isEnabled();
		}
		
		@Override
		public boolean isAccountNonExpired() {
			return isEnabled();
		}
		
		@Override
		public String getUsername() {
			return username;
		}
		
		@Override
		public String getPassword() {	
			return password;
		}
		
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return userAuths;
		}
	}

}
