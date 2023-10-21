public class HomomorphicEncryptionAddition
{
    
    public static void main(String[] args)
    {
       // Addition
BigInteger c = DGKOperations.encrypt(10, pk);
c = DGKOperations.add(c, c, pk); 
// c = 10 + 10 = 20. Notice both arguments need to be encrypted. c is still encrypted!
// Scalar addition
BigInteger d = DGKOperations.encrypt(10, pk);
d = DGKOperations.add_plaintext(d, 10, pk);
// d = 10 + 10 = 20. The second argument must be a plaintext if using plaintext addition! 
// d is still encrypted!
    }
}