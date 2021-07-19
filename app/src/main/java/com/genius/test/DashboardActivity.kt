package com.genius.test

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.genius.test.adapter.ImageAdapter
import com.genius.test.database.UserEntity
import com.genius.test.model.Artist
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class DashboardActivity : AppCompatActivity() {


    private lateinit var viewModel: MainActivityViewModel

    private lateinit var mAuth: FirebaseAuth

    lateinit  var imageAdapter: ImageAdapter

    lateinit var layoutManager: LinearLayoutManager

    lateinit var name_txt: EditText

    lateinit var email_txt: EditText

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

        var artistList: ArrayList<Artist> = ArrayList<Artist>()

        searchedListRV = findViewById(R.id.searchedListRV)

        var linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        searchedListRV.layoutManager = linearLayoutManager

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

        var data: Button = headerLayout.findViewById(R.id.data)

        name = mAuth.currentUser?.displayName.toString()

        email = mAuth.currentUser?.email.toString()

        im = mAuth.currentUser?.photoUrl.toString()

        photo = currentUser?.photoUrl.toString()

        image.load(photo)

        runBlocking {
            var job: Job = launch(context = Dispatchers.Default) {
                database = FirebaseDatabase.getInstance().getReference("Artists")

                var artist1 = Artist(
                    "Britney Spears",
                    "https://pic.lyricshub.ru/img/b/g/p/z/ytMepWZpGB.jpg",
                    "Lucky",
                    "Lucky is a song recorded by American singer Britney Spears, for her second studio album, Oops!... I Did It Again (2000). It was released on July 25, 2000, by Jive Records as the second single from the album. After meeting with songwriters Max Martin and Rami Yacoub in Sweden, the singer recorded numerous songs for the album, including Lucky."
                )


                database.child(artist1.artist.toString()).setValue(artist1).addOnSuccessListener {


                }.addOnFailureListener {


                }

                var artist2 = Artist(
                    "Justin Bieber",
                    "https://th.bing.com/th/id/OIP.wfg6sIM0YAd9wYn9Vj8QtwHaJf?pid=ImgDet&rs=1",
                    "Baby",
                    "Baby is a song by Canadian singer Justin Bieber, alongside American rapper Ludacris. It was released as the lead single on Bieber's debut album, My World 2.0. The track was written by Bieber, Christina Milian, Tricky Stewart (who worked with Bieber on a previous single One Time, and American R&B singer The-Dream and produced by the latter two."
                )

                database.child(artist2.artist.toString()).setValue(artist2).addOnSuccessListener {


                }.addOnFailureListener {


                }

                var artist3 = Artist(
                    "Michael Jackson",
                    "https://th.bing.com/th/id/OIP.IhlADTDIs8F-qdTQSZqoQwHaMb?pid=ImgDet&rs=1",
                    "Beat it",
                    "The song was released on February 14, 1983 as the album's third single. The guitar solo is performed by Eddie Van Halen, lead guitarist of hard rock band Van Halen. When initially contacted by Michael Jackson's producer, Van Halen thought he was receiving a prank call."
                )

                database.child(artist3.artist.toString()).setValue(artist3).addOnSuccessListener {


                }.addOnFailureListener {


                }
            }
            job.join()
        }




        runBlocking {
            var job2 = launch(Dispatchers.Default) {


                artistList = readData("Michael Jackson", artistList)

                artistList = readData("Justin Bieber", artistList)

                artistList = readData("Britney Spears", artistList)

                viewModel =
                    ViewModelProviders.of(this@DashboardActivity)
                        .get(MainActivityViewModel::class.java)

                viewModel.delete()


                user = UserEntity(0, name, email, im)

                viewModel.insertUserInfo(user)

                user = viewModel.loadSingle(email)


            }

            job2.join()
        }

        name_txt.setText(user.name)

        email_txt.setText(user.email)




        data.setOnClickListener {


               imageAdapter = ImageAdapter(
                   this@DashboardActivity,
                   artistList
               )


               searchedListRV.adapter = imageAdapter

            }


    }


     fun readData(artistName: String, artistList: ArrayList<Artist>):ArrayList<Artist> {


         database = FirebaseDatabase.getInstance().getReference("Artists")


         database.child(artistName).get().addOnSuccessListener {


             if (it.exists()) {

                 var photo = it.child("photo").value.toString()
                 var title = it.child("title").value.toString()
                 var description = it.child("description").value.toString()
                 var artistName = it.child("artist").value.toString()

                var  artist = Artist(artistName, photo, title, description)


                 artistList.add(artist)


             } else {


             }


         }.addOnFailureListener {


         }

         return artistList

     }

}





