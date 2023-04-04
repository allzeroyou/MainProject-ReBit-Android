package kr.ac.duksung.rebit

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kr.ac.duksung.rebit.databinding.ActivityRecycleBinding

class RecycleActivity : AppCompatActivity() {
//    var cnt =0
//    var str_cnt=""
//    var final_path_str=""
//private var currentIndex = 0

    private lateinit var binding: ActivityRecycleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle)


        // 1. 화면이 클릭되었다는 것을 알아야 합니다! (프로그램이)
        val image_view = findViewById<ImageView>(R.id.cardnews_view)
        //val image2 = findViewById<ImageView>(R.id.cardnews_2)
        val next_btn = findViewById<Button>(R.id.right_btn)
        val before_btn = findViewById<Button>(R.id.left_btn)
        val camera_btn = findViewById<Button>(R.id.camera_btn)
        val tip_button = findViewById<Button>(R.id.tip_button)
        val guide_button = findViewById<Button>(R.id.guide_btn)
        val close_btn = findViewById<Button>(R.id.close_btn)


        next_btn.setOnClickListener {
            Toast.makeText(this, "다음 클릭 완료", Toast.LENGTH_LONG).show()
            image_view.setImageResource(R.drawable.cardnews_1)
        }


        before_btn.setOnClickListener {
            Toast.makeText(this, "이전 클릭 완료", Toast.LENGTH_LONG).show()

            // 2. 화면이 클릭되면, 다음 화면으로 넘어가서, 사진을 크게 보여줌! + 환경부 인스타그램 연결
//            val intent = Intent(this, CardNewsActivity::class.java)
//            cnt--
//            intent.putExtra("data", cnt)
//             startActivity(intent)
            image_view.setImageResource(R.drawable.cardnews_0)
        }

        image_view.setOnClickListener {
            var intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/p/CpjvrxZrkTP/"))
            startActivity(intent)
        }

        camera_btn.setOnClickListener {
            var intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }


        // 뷰 바인딩
//        binding = ActivityRecycleBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.tipButton.setOnClickListener {
//            // Dialog만들기
//            val mDialogView = LayoutInflater.from(this).inflate(R.layout.today_tip_dialog, null)
//            val mBuilder = AlertDialog.Builder(this)
//                .setView(mDialogView)
//
//            val mAlertDialog=mBuilder.show()
//
//            // 포인트 획득 버튼 클릭시
//            val okButton = mDialogView.findViewById<Button>(R.id.successButton)
//            okButton.setOnClickListener {
//
//                Toast.makeText(this, "포인트를 획득했습니다!", Toast.LENGTH_SHORT).show()
//                mAlertDialog.dismiss()
//            }
//
//        }

        close_btn.setOnClickListener {
            finish()
        }

    }


}