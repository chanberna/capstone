package com.example.unichatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        // This call the parent constructor.
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class.
        setContentView(R.layout.activity_main)

        // Initialize mAuth.
        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()

        userList = ArrayList()
        adapter = UserAdapter(this, userList)

        userRecyclerView = findViewById(R.id.userRecyclerView)

        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter = adapter

        // Get inside and read the database.
        mDbRef.child("user").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                userList.clear()

                for(postSnapshot in snapshot.children) {
                    val currentUser = postSnapshot.getValue(User::class.java)

                    // It will show the current login user.
                    if(mAuth.currentUser?.uid != currentUser?.uid) {
                        userList.add(currentUser!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }) // mDbRef

    } // onCreate

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout) {
            // Logic for logout.
            mAuth.signOut()

            // It will direct to the main page when click on Log Out.
            val intent = Intent(this@MainActivity, LogIn::class.java)
            finish()
            startActivity(intent)
            return true
        }

        if(item.itemId == R.id.profile) {
            // It will direct to my profile page.
            val intent = Intent(this@MainActivity, MyProfileActivity::class.java)
            startActivity(intent)
            return true
        }
        return true

    } // onOptions...

} // End