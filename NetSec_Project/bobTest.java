
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.List;

import security.DGK.DGKOperations;
import security.DGK.DGKPrivateKey;
import security.DGK.DGKPublicKey;
import security.DGK.DGKSignature;
import security.elgamal.ElGamalCipher;
import security.elgamal.ElGamalPrivateKey;
import security.elgamal.ElGamalPublicKey;
import security.elgamal.ElGamalSignature;
import security.elgamal.ElGamal_Ciphertext;
import security.gm.GMCipher;
import security.gm.GMPrivateKey;
import security.gm.GMPublicKey;
import security.misc.CipherConstants;
import security.misc.HomomorphicException;
import security.misc.NTL;
import security.paillier.PaillierCipher;
import security.paillier.PaillierPrivateKey;
import security.paillier.PaillierPublicKey;
import security.paillier.PaillierSignature;
import security.socialistmillionaire.alice;
import security.socialistmillionaire.bob;

import security.paillier.PaillierKeyPairGenerator;
import java.security.KeyPair;
import security.DGK.DGKKeyPairGenerator;
import security.elgamal.ElGamalKeyPairGenerator;
import java.security.SecureRandom;


public class bobTest {
    
    public static void main(String[] args)
    {
        try{
        ServerSocket bob_socket = null;
Socket bob_client = null;
int KEY_SIZE = 1024;
bob andrew = null;
    	
// Build all Key Pairs
PaillierKeyPairGenerator p = new PaillierKeyPairGenerator();
p.initialize(KEY_SIZE, null);
KeyPair pe = p.generateKeyPair();

DGKKeyPairGenerator d = new DGKKeyPairGenerator();
d.initialize(KEY_SIZE, null);
KeyPair DGK = d.generateKeyPair();
    	
ElGamalKeyPairGenerator pg = new ElGamalKeyPairGenerator();
pg.initialize(KEY_SIZE, new SecureRandom());
KeyPair el_gamal = pg.generateKeyPair();
    		
bob_socket = new ServerSocket(9254);
bob_client = bob_socket.accept();
    	
// Note: Alice automatically gets the public keys!
andrew = new bob(bob_client, pe, DGK, el_gamal);
        }
catch(IOException e)
{
e.printStackTrace();
}
    }
    
}
