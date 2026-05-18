<?xml version="1.0" encoding="utf-8"?>
<androidx.swiperefreshlayout.widget.SwipeRefreshLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/swipeRefresh"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#FFF8F0">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:padding="12dp">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="✨ Color Trends &amp; Inspiration"
            android:textSize="28sp"
            android:textStyle="bold"
            android:textColor="#4A3780"
            android:fontFamily="sans-serif-medium"
            android:layout_marginBottom="4dp"/>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="AI-powered fashion insights for weavers"
            android:textSize="14sp"
            android:textColor="#79747E"
            android:layout_marginBottom="16dp"/>

        <View
            android:layout_width="60dp"
            android:layout_height="3dp"
            android:background="#6750A4"
            android:layout_marginBottom="16dp"/>

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/trendRecyclerView"
            android:layout_width="match_parent"
            android:layout_height="0dp"
            android:layout_weight="1"
            app:spanCount="1"/>

        <TextView
            android:id="@+id/emptyView"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="🌸 No trends available"
            android:textAlignment="center"
            android:visibility="gone"
            android:padding="32dp"
            android:textSize="16sp"
            android:textColor="#79747E"/>

    </LinearLayout>

</androidx.swiperefreshlayout.widget.SwipeRefreshLayout>