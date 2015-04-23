package vn.kms.launch.contactmgr;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class AuthenticatedExternalWebService extends AuthenticationWithToken{
    private ExternalWebServiceStub externalWebServiceStub;

    public AuthenticatedExternalWebService(Object aPrincipal,
            Object aCredentials,
            Collection<? extends GrantedAuthority> anAuthorities) {
        super(aPrincipal, aCredentials, anAuthorities);
        // TODO Auto-generated constructor stub
    }
    public void setExternalWebService(ExternalWebServiceStub externalWebServiceStub){
        this.externalWebServiceStub = externalWebServiceStub;
    }
    
     public ExternalWebServiceStub getExternalWebService(){
         return this.externalWebServiceStub;
     }

}
