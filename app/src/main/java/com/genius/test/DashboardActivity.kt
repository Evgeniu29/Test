package com.genius.test

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.genius.test.adapter.ImageAdapter
import com.genius.test.database.UserEntity
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*


class DashboardActivity : AppCompatActivity() {


    var artistList: ArrayList<Artist> = arrayListOf<Artist>()

    private lateinit var viewModel: MainActivityViewModel

    private lateinit var mAuth: FirebaseAuth

    lateinit var imageAdapter: ImageAdapter

    lateinit var layoutManager: LinearLayoutManager

    lateinit var name_txt: TextView

    lateinit var email_txt: TextView

    lateinit var dataInfo: TextView

    lateinit var searchedListRV: RecyclerView
    var searchedText: String? = ""

    lateinit var textInfo: TextView

    var list: List<UserEntity> = emptyList()

    var photo: String = ""

    var name = ""

    var email = ""

    var im = ""

    private val pageStart: Int = 1

    lateinit var user: UserEntity



    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        mAuth = FirebaseAuth.getInstance()

        val currentUser = mAuth.currentUser

        var drawer: DrawerLayout = findViewById(R.id.drawer_layout)

        drawer.openDrawer(GravityCompat.START)

        var mNavigationView: NavigationView = findViewById(R.id.nav_view)

        mNavigationView.bringToFront()

        var headerLayout = mNavigationView.inflateHeaderView(R.layout.header)

        name_txt = headerLayout.findViewById(R.id.name_txt)

        email_txt = headerLayout.findViewById(R.id.email_txt)

        dataInfo = headerLayout.findViewById(R.id.dataInfo)

        var image: de.hdodenhof.circleimageview.CircleImageView =
            headerLayout.findViewById(R.id.profile_image)

        var logout: Button = headerLayout.findViewById(R.id.logout)

        name = mAuth.currentUser?.displayName.toString()

        email = mAuth.currentUser?.email.toString()

        im = mAuth.currentUser?.photoUrl.toString()

        photo = currentUser?.photoUrl.toString()

        image.load(photo)

        logout.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()

        }

        user = UserEntity(0, name, email, im)

        searchedListRV = findViewById(R.id.searchedListRV)

        var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(
            this.applicationContext
        )

     searchedListRV.layoutManager = linearLayoutManager

        startLongRunningTask()

        imageAdapter = ImageAdapter(
            this@DashboardActivity,
            artistList
        )

        searchedListRV.adapter = imageAdapter


    }

    fun startLongRunningTask() {


        GlobalScope.launch(Dispatchers.Main) {



            async(Dispatchers.IO) {


                viewModel =
                    ViewModelProviders.of(this@DashboardActivity)
                        .get(MainActivityViewModel::class.java)

                viewModel.delete()

                viewModel.insertUserInfo(user)

                user = viewModel.loadSingle(email)


            }


            name_txt.text = user.name

            email_txt.text = user.email

           async(Dispatchers.IO) {

                database = FirebaseDatabase.getInstance().getReference("Artists")

                var artist = Artist(
                    "Britney Spears",
                    "https://pic.lyricshub.ru/img/b/g/p/z/ytMepWZpGB.jpg",
                    "Lucky",
                    "Lucky is a song recorded by American singer Britney Spears, for her second studio album, Oops!... I Did It Again (2000). It was released on July 25, 2000, by Jive Records as the second single from the album. After meeting with songwriters Max Martin and Rami Yacoub in Sweden, the singer recorded numerous songs for the album, including Lucky. The song chronicles the story of a famous young actress named Lucky, who, despite seemingly having it all – fame, wealth, beauty – is truly lonely and unhappy on the inside. It received critical acclaim, with critics praising its melody and rhythm, and Spears vocals."
                )


                database.child(artist.artist.toString()).setValue(artist).addOnSuccessListener {


                    Toast.makeText(this@DashboardActivity, "Successfully Saved", Toast.LENGTH_SHORT)
                        .show()

                }.addOnFailureListener {

                    Toast.makeText(this@DashboardActivity, "Failed", Toast.LENGTH_SHORT).show()


                }


                var artist1 = Artist(
                    "Justin Bieber",
                    "https://th.bing.com/th/id/OIP.wfg6sIM0YAd9wYn9Vj8QtwHaJf?pid=ImgDet&rs=1",
                    "Baby",
                    "Baby is a song by Canadian singer Justin Bieber, alongside American rapper Ludacris. It was released as the lead single on Bieber's debut album, My World 2.0. The track was written by Bieber, Christina Milian, Tricky Stewart (who worked with Bieber on a previous single One Time, and American R&B singer The-Dream and produced by the latter two.  It was available for digital download on January 18, 2010. The song received airplay directly after release, officially impacting mainstream and rhythmic radio on January 26, 2010. The song is uptempo R&B, blending together dance-pop and hip-hop elements, while using influences of doo-wop music. The song has received positive reviews from critics who complimented the song's effective lyrics and chorus and commended Ludacris' part and the song's ability to have an urban twist."
                )

                database.child(artist1.artist.toString()).setValue(artist1).addOnSuccessListener {


                    Toast.makeText(this@DashboardActivity, "Successfully Saved", Toast.LENGTH_SHORT)
                        .show()

                }.addOnFailureListener {

                    Toast.makeText(this@DashboardActivity, "Failed", Toast.LENGTH_SHORT).show()


                }


               var artist2 = Artist(
                    "Michael Jackson",
                    "https://th.bing.com/th/id/OIP.IhlADTDIs8F-qdTQSZqoQwHaMb?pid=ImgDet&rs=1",
                    "Beat it",
                    "The song was released on February 14, 1983 as the album's third single. The guitar solo is performed by Eddie Van Halen, lead guitarist of hard rock band Van Halen. When initially contacted by Michael Jackson's producer, Van Halen thought he was receiving a prank call. Musicians of the group Toto contributed lead/bass guitar, synthesizer and drums to the recording. Beat It received two 1984 Grammy Awards: Record of the Year and Best Male Rock Vocal Performance. The famous music video is particularly notable for a synchronized dancing, a Jackson trademark that helped establish him as an international pop icon."
                )

                database.child(artist2.artist.toString()).setValue(artist2).addOnSuccessListener {


                    Toast.makeText(this@DashboardActivity, "Successfully Saved", Toast.LENGTH_SHORT)
                        .show()

                }.addOnFailureListener {

                    Toast.makeText(this@DashboardActivity, "Failed", Toast.LENGTH_SHORT).show()


                }

            }



           async(Dispatchers.IO) {

                readData("Michael Jackson")

                readData("Justin Bieber")

                readData("Britney Spears")

            }

        }


    }



    private fun readData(artist: String) {



        database = FirebaseDatabase.getInstance().getReference("Artists")


        database.child(artist).get().addOnSuccessListener {

            if (it.exists()) {

                var photo = it.child("photo").value.toString()
                var title = it.child("title").value.toString()
                var description = it.child("description").value.toString()
                var artist_name = it.child("artist").value.toString()

                var  artist = Artist(artist_name,  photo, title, description )

                artistList.add(artist)


                Toast.makeText(this, "Successfuly Read", Toast.LENGTH_SHORT).show()



            } else {

                Toast.makeText(this, "User Doesn't Exist", Toast.LENGTH_SHORT).show()


            }

        }.addOnFailureListener {

            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()

        }


    }


}





