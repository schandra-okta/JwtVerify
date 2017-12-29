package com.okta.jwtverify;

import java.math.BigInteger;
import java.security.spec.RSAPublicKeySpec;
import io.jsonwebtoken.*;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Properties;

public class App 
{
    final static Properties configuration = new Properties();
    public static void main( String[] args ) throws Exception
    {
        configuration.load(new FileInputStream(args[0]));
        BigInteger pubExp = new BigInteger(1, Base64.getUrlDecoder().decode(configuration.getProperty("publicKeyExponent")));
        BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(configuration.getProperty("publicKeyModulus")));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(modulus, pubExp);
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);
        try{
            Jwts.parser().setSigningKey(key).parse(args[1]);
/*            
            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(args[1]).getBody();
            for(Object claimKey:claims.keySet())
            {
                System.out.println(claimKey+" = "+claims.get(claimKey));
            }
*/
            System.out.println("****Signtaure verified.");
        }
        catch(Exception e)
        {
            System.out.println("*****Signature did not verify!!");
            e.printStackTrace();
        }
    }
}