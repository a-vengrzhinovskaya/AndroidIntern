package com.example.androidintern

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidintern.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val JSON_STRING = "[{\n" +
        "    \"name\": \"(Приёмная)\",\n" +
        "    \"phone\": \"+375 (2239) 7-17-80\",\n" +
        "    \"type\": \"\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"name\": \"(Бухгалтерия)\",\n" +
        "    \"phone\": \"+375 (2239) 7-17-64\",\n" +
        "    \"type\": \"\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"name\": \"(Бухгалтерия)\",\n" +
        "    \"phone\": \"+375 (2239) 7-18-08\",\n" +
        "    \"type\": \"\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"name\": \"(Юридическое бюро)\",\n" +
        "    \"phone\": \"+375 (2239) 7-17-63\",\n" +
        "    \"type\": \"\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"name\": \"(Отдел правовой и кадровой работы)\",\n" +
        "    \"phone\": \"+375 (2239) 7-17-93\",\n" +
        "    \"type\": \"\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"name\": \"(Отдел материально-технического снабжения)\",\n" +
        "    \"phone\": \"+375 (2239) 7-18-12\",\n" +
        "    \"type\": \"\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"name\": \"\",\n" +
        "    \"phone\": \"+375 44 712 36 26\",\n" +
        "    \"type\": \"Сектор сбыта бумаги\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"name\": \"(Реализация на внутренний рынок)\",\n" +
        "    \"phone\": \"+375 (2239) 7-17-79\",\n" +
        "    \"type\": \"Сектор сбыта бумаги\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"name\": \"(Реализация на внешний рынок)\",\n" +
        "    \"phone\": \"+375 (2239) 4-11-77\",\n" +
        "    \"type\": \"Сектор сбыта бумаги\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"name\": \"(Реализация на внутренний рынок)\",\n" +
        "    \"phone\": \"+375 (2239) 7-18-25\",\n" +
        "    \"type\": \"Сектор сбыта бумаги\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"name\": \"\",\n" +
        "    \"phone\": \"+375 44 580 09 70\",\n" +
        "    \"type\": \"Сектор сбыта продукции деревообработки\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"name\": \"(Реализация продукции деревообработки)\",\n" +
        "    \"phone\": \"+375 (2239) 7-18-42\",\n" +
        "    \"type\": \"Сектор сбыта продукции деревообработки\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"name\": \"(Реализация продукции деревообработки)\",\n" +
        "    \"phone\": \"+375 (2239) 3-64-71\",\n" +
        "    \"type\": \"Сектор сбыта продукции деревообработки\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"name\": \"\",\n" +
        "    \"phone\": \"+375 29 352 25 20\",\n" +
        "    \"type\": \"Реализация домов, бань, беседок, клеёного бруса\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"name\": \"\",\n" +
        "    \"phone\": \"+375 (2239) 7-18-43\",\n" +
        "    \"type\": \"Реализация домов, бань, беседок, клеёного бруса\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"name\": \"(приемная)\",\n" +
        "    \"phone\": \"+375 (2239) 7-17-80\",\n" +
        "    \"type\": \"Факс\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"name\": \"(отдел сбыта)\",\n" +
        "    \"phone\": \"+375 (2239) 7-17-79\",\n" +
        "    \"type\": \"Факс\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"name\": \"(отдел материально-технического снабжения)\",\n" +
        "    \"phone\": \"+375 (2239) 7-17-82\",\n" +
        "    \"type\": \"Факс\"\n" +
        "  },\n" +
        "  {\n" +
        "    \"name\": \"(служба главного энергетика)\",\n" +
        "    \"phone\": \"+375 (2239) 7-18-06\",\n" +
        "    \"type\": \"Факс\"\n" +
        "  }]"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = NumberAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        searchNumber()
    }

    private fun getNumbers(): List<PhoneNumber> {
        val itemType = object : TypeToken<List<PhoneNumber>>() {}.type
        return Gson().fromJson(JSON_STRING, itemType)
    }

    private fun initRecyclerView() {
        binding.rvPhoneNumbers.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter.submitList(getNumbers())
        binding.rvPhoneNumbers.adapter = adapter
    }

    private fun searchNumber() {
        binding.toolbar.etSearch.addTextChangedListener {
            val filteredNumbers = getNumbers().filter {
                it.name.contains(binding.toolbar.etSearch.text, true) ||
                        it.type.contains(binding.toolbar.etSearch.text, true) ||
                        it.phone.contains(binding.toolbar.etSearch.text)
            }
            adapter.setContent(filteredNumbers)
        }
    }
}