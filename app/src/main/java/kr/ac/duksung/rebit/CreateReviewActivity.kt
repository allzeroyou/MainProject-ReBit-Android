package kr.ac.duksung.rebit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.duksung.rebit.network.RetrofitService
import retrofit2.Retrofit
import kr.ac.duksung.rebit.network.RetofitClient
import kr.ac.duksung.rebit.network.dto.ApiResponse
import kr.ac.duksung.rebit.network.dto.ReviewCommentsVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateReviewActivity() : AppCompatActivity() {
    private lateinit var retrofit: Retrofit
    private lateinit var retrofitService: RetrofitService

    private val TAG = "CreateReview"
    private val uriList = ArrayList<Uri>() // ArrayList object to store URIs of selected images

    lateinit var recyclerView: RecyclerView // RecyclerView to display selected images
    lateinit var adapter: MultiImageAdapter // Adapter to apply to the RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_review)

        //서버 연결
        initRetrofit()

        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val reviewEditText = findViewById<EditText>(R.id.reviewEditText)

        // Button to open the photo album
        val btnGetImage = findViewById<Button>(R.id.getImage)
        btnGetImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            startActivityForResult(intent, 2222)
        }

        recyclerView = findViewById(R.id.photoRecyclerView)

        val close_btn = findViewById<Button>(R.id.close_btn)

        close_btn.setOnClickListener {
            finish()
        }

        // 리뷰 등록 버튼 클릭 시
        val submitBtn = findViewById<Button>(R.id.submit_btn)

        submitBtn.setOnClickListener {
            // 서버에 리뷰 데이터를 보내고 (post)

            val review = ReviewCommentsVO(0,
                0,
                ratingBar.rating.toInt(),
                "photo",
                reviewEditText.text.toString())

            retrofitService.postReviewComments(review)
                .enqueue(object : Callback<ApiResponse<ReviewCommentsVO>> {
                    override fun onResponse(
                        call: Call<ApiResponse<ReviewCommentsVO>>,
                        response: Response<ApiResponse<ReviewCommentsVO>>,
                    ) {
                        if (response.isSuccessful) {
                            val result: ApiResponse<ReviewCommentsVO>? = response.body()
                            val data = result?.getResult()

                            Log.d(TAG, "onResponse success: " + result?.toString())
                            Log.d(TAG, "data: " + data?.toString())
                        } else {
                            Log.d(TAG, "onResponse error: " + response.errorBody().toString())
                        }
                    }

                    override fun onFailure(call: Call<ApiResponse<ReviewCommentsVO>>, t: Throwable) {
                        Log.e(TAG, "onFailure: ${t.message}")
                    }
                })

            Toast.makeText(this, "리뷰가 등록되었습니다!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ReviewActivity::class.java)
            startActivity(intent)
        }
    } //OnCreate

    //서버 연결
    private fun initRetrofit() {
        retrofit = RetofitClient.getInstance()
        retrofitService = retrofit.create(RetrofitService::class.java)
    }

    // 통신
//    fun postReviewComments() {
//        //enqueue : 비동기식 통신을 할 때 사용/ execute: 동기식
//        val reviewComment = ReviewCommentsVO(0, 20200848, 5, "photo", "앗살라말라이쿰")
//
//        retrofitService.postReviewComments(reviewComment).enqueue(object :
//            Callback<ApiResponse<ReviewCommentsVO>> {
//            override fun onResponse(
//                call: Call<ApiResponse<ReviewCommentsVO>>,
//                response: Response<ApiResponse<ReviewCommentsVO>>
//            ) {
//                if (response.isSuccessful) {
//                    // 통신 성공시
//                    val result: ApiResponse<ReviewCommentsVO>? = response.body()
//                    val data = result?.getResult()
//
//                    Log.d("ReviewComments", "onResponse 성공: " + result?.toString())
//                    Log.d("ReviewComments", "data : " + data?.toString())
//                }
//            }
//
//            override fun onFailure(
//                call: Call<ApiResponse<ReviewCommentsVO>>,
//                t: Throwable
//            ) {
//                Log.e("ReviewComments", "onFailure : ${t.message} ");
//            }
//        })
//        // 화면 이동
//        Toast.makeText(this, "리뷰가 등록되었습니다!", Toast.LENGTH_SHORT).show()
//        val intent = Intent(this, ReviewActivity::class.java)
//        startActivity(intent)
//    }


    // Method executed after returning from the photo album
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        data?.let {
//            // No image selected
//            if (it.data == null) {
//                Toast.makeText(applicationContext, "No image selected.", Toast.LENGTH_LONG).show()
//            } else {
//                // Single image selected
//                if (it.clipData == null) {
//                    Log.e("single choice: ", it.data.toString())
//                    val imageUri = it.data!!
//                    uriList.add(imageUri)
//
//                    adapter = MultiImageAdapter(uriList, applicationContext)
//                    recyclerView.adapter = adapter
//                    recyclerView.layoutManager =
//                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
//                }
//                // Multiple images selected
//                else {
//                    val clipData = it.clipData!!
//                    Log.e("clipData", clipData.itemCount.toString())
//
//                    if (clipData.itemCount > 10) {
//                        Toast.makeText(
//                            applicationContext,
//                            "You can select up to 10 photos.",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    } else {
//                        Log.e(TAG, "multiple choice")
//
//                        for (i in 0 until clipData.itemCount) {
//                            val imageUri =
//                                clipData.getItemAt(i).uri // Get the URIs of the selected images
//                            try {
//                                uriList.add(imageUri) // Add the URI to the list
//
//                            } catch (e: Exception) {
//                                Log.e(TAG, "File select error", e)
//                            }
//                        }
//
//                        adapter = MultiImageAdapter(uriList, applicationContext)
//                        recyclerView.adapter = adapter // Set the adapter to the RecyclerView
//                        recyclerView.layoutManager = LinearLayoutManager(
//                            this,
//                            LinearLayoutManager.HORIZONTAL,
//                            true
//                        ) // Apply horizontal scrolling to the RecyclerView
//                    }
//                }
//            }
//        }
//
//    }


}
