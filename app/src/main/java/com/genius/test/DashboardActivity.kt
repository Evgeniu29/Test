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
import com.genius.test.retrofit.ApiInterface
import com.genius.test.retrofit.AppClient
import com.genius.test.retrofit.response.PhotosItem
import com.genius.test.retrofit.response.SearchListResponse
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DashboardActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    private lateinit var mAuth: FirebaseAuth

    private val appClient = AppClient.getClient(this@DashboardActivity)

    private val apiInterface = appClient.create(ApiInterface::class.java)

    lateinit var imageAdapter: ImageAdapter

    lateinit var layoutManager: LinearLayoutManager

    lateinit var name_txt: EditText

    lateinit var email_txt: EditText

    lateinit var dataInfo: TextView

    lateinit var searchedListRV: RecyclerView
    var searchedText: String? = ""

    lateinit var textInfo: TextView

    var photoList: ArrayList<PhotosItem> = ArrayList()

    var list: ArrayList<UserEntity> = ArrayList()

    var photo: String = ""

    private val listViewType = mutableListOf<Int>()

    private var isLoading = false

    private val progressBar: ProgressBar? = null

    var name = ""

    var email = ""

    var im = ""

    var visibleItemCount = 0
    var totalItemCount: Int = 1
    var firstVisiblesItems = 0
    var totalPages = 15 // get your total pages from web service first response

    var current_page = 0

    var canLoadMoreData = true // make this variable false while your web service call is going on.


    var linearLayoutManager: LinearLayoutManager? = null

    lateinit var user: UserEntity

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

        linearLayoutManager = LinearLayoutManager(this)

        searchedListRV.setLayoutManager(linearLayoutManager)



        GlobalScope.launch {

            delay(3000L)


            viewModel = ViewModelProviders.of(this@DashboardActivity).get(MainActivityViewModel::class.java)


            viewModel.delete()

            viewModel.insertUserInfo(user)

            user = viewModel.loadSingle(email)

            setCurrentItem(current_page)


            list = viewModel.getAllUsers() as ArrayList<UserEntity>

            for (i in list) {
                println(i.email + i.name + i.id + i.image)
            }


        }.start()

        name_txt.setText(user.name)

        email_txt.setText(user.email)






        searchedListRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = linearLayoutManager!!.childCount
                    totalItemCount = linearLayoutManager!!.itemCount
                    firstVisiblesItems = linearLayoutManager!!.findFirstVisibleItemPosition()
                    if (canLoadMoreData) {
                        if (visibleItemCount + firstVisiblesItems >= totalItemCount) {
                            if (current_page < totalPages) {
                                canLoadMoreData = false

                                setCurrentItem(current_page)

                                canLoadMoreData = true



                            }


                        }
                    }
                }
            }


        })


    }

    private fun setCurrentItem(current_page: Int) {

        try {

            val observable = apiInterface.getSearchList(current_page, 15)
            observable.enqueue(object : Callback<SearchListResponse> {
                override fun onResponse(
                        call: Call<SearchListResponse>,
                        response: Response<SearchListResponse>
                ) {

                    if (response.isSuccessful) {
                        if (!response.body()?.photos.isNullOrEmpty()) {
                            photoList.clear()



                            response.body()?.photos?.let { photoList.addAll(it) }
                            imageAdapter = ImageAdapter(
                                    this@DashboardActivity,
                                    photoList, object : ImageAdapter.SetOnClickListener {
                                override fun itemClicked(
                                        position: Int,
                                        photosItem: PhotosItem
                                ) {

                                }

                            }
                            )

                            searchedListRV.adapter = imageAdapter

                        }
                    }
                }

                override fun onFailure(call: Call<SearchListResponse>, t: Throwable) {

                    t.printStackTrace()
                }

            })
        } catch (e: Exception) {

            e.printStackTrace()
        }

    }
}



