package com.example.smallbusinessmanager

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class CashFlowActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var cashFlowListView
