package com.example.homework_4
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale
import com.bumptech.glide.Glide
import java.text.ParseException


class RecyclerViewAdapter(private val eventsList: ArrayList<Event>) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    // set up the reference to the views in the row_item.xml
    inner class MyViewHolder (itemView: View): RecyclerView.ViewHolder (itemView) {
        val eventTitle = itemView.findViewById<TextView>(R.id.eventName_textView)
        val eventLocation = itemView.findViewById<TextView>(R.id.eventLocation_textView)
        val eventDate = itemView.findViewById<TextView>(R.id.eventDate_textView)
        val eventPriceRange = itemView.findViewById<TextView>(R.id.priceRange_textView)
        val eventImage = itemView.findViewById<ImageView>(R.id.imageView)
        val seeEvent = itemView.findViewById<Button>(R.id.seeEvent_button)
    }

    // Create new views, not sure, taken from class notes
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return MyViewHolder(view)
    }

    // Add data to the views, ?: = if null, use the string after.
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = eventsList[position]
        Log.d("RecyclerViewAdapter", "HERE!!!!!!!! Event name: ${currentItem.name}") // for debugging

        // Event name
        holder.eventTitle.text = currentItem.name ?: "TBA"

        // Event Name and location - Name, Address, City, State, Country. build a string and put it in the text view
        val venue = currentItem._embedded.venues[0]
        val venueDetails =
            "${venue.name ?: "Venue - TBA"}\n${venue.address?.line1 ?: ""} \n${venue.city?.name ?: ""}, ${venue.state?.name ?: ""} \n${venue.country?.name ?: ""}"
        holder.eventLocation.text = venueDetails

        // Event Date - Date and Time, send to getTime() to format, if null replace with TBA getTime() needs to handle null
        val date = currentItem.dates.start.localDate ?: "Event Date - TBA"
        val time = currentItem.dates.start.localTime ?: ""
        val dateTime = getTime(date, time)
        holder.eventDate.text = dateTime

        // Event Price Range - Min and Max, if null replace with empty string and make field invisible
        val priceRange: String
        if (!currentItem.priceRanges.isNullOrEmpty()) {
            priceRange = "$${currentItem.priceRanges[0].min} - $${currentItem.priceRanges[0].max}"
        } else {
            priceRange = "" // holder seems to need a string value, otherwise it throws an error
            holder.eventPriceRange.visibility = View.INVISIBLE
        }
        holder.eventPriceRange.text = priceRange

        // Event Image - Use Glide to get image, if null use default image
        val bestImageUrl = getBestImageUrl(currentItem.images) // getBestImageUrl() uses getMaxByOrNull() to get the image with the highest area
        if (bestImageUrl != null) {
            Glide.with(holder.itemView)
                .load(bestImageUrl)
                .error(R.drawable.default_image) // any error including null will use the default image
                .into(holder.eventImage)
        }

        // get the venue URL and tie it to the seeEvent button, using the URL in the event object
        // keeps triggering some sort of session cancellation on ticketMaster for bot activity
        // using the venue URL instead of the event URL
        holder.seeEvent.setOnClickListener {
            val url = currentItem._embedded.venues[0].url ?: currentItem.url
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            holder.itemView.context.startActivity(intent)
        }
    }

    // Not sure I need this, but it was in the class notes and I don't want to break anything
    override fun getItemCount(): Int {
        // Return the size of your dataset (invoked by the layout manager)
        return eventsList.size
    }

    // Function to format the date and time import (java.text.SimpleDateFormat, java.util.Locale)
    private fun getTime(date: String, time: String): String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        val targetFormat = SimpleDateFormat("'Date:' yyyy-MM-dd '@' h:mm a", Locale.US)
        val date = date.trim()
        val time = time.trim()
        val originalDateTime = "$date $time"

        // new york location crashes the app, logcat shows one of the dates has a space at the end
        val originalDate = try {
            originalFormat.parse(originalDateTime)
        } catch (e: ParseException) {
            null
        }
        return if (originalDate != null) {
            targetFormat.format(originalDate)
        } else {
            "Event Date - TBA"
        }
    }

    private fun getBestImageUrl(images: List<Image>): String? {
        // Find the image with the maximum resolution
        val bestImage = images.maxByOrNull { it.width * it.height }
        //Log.d("RecyclerViewAdapter", "Best image URL with maxByOrNull: ${bestImage?.url}")
        return bestImage?.url
    }
}