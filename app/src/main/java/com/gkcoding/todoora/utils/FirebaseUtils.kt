import com.google.firebase.auth.FirebaseAuth

data object FirebaseUtils {
    fun getCurrentUserEmail(): String? {
        return FirebaseAuth.getInstance().currentUser?.email
    }
}