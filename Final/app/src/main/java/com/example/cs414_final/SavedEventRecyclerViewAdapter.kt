package com.example.cs414_final

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
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

private const val TAG = "SavedEventRecyclerViewAdapter"

class SavedEventRecyclerViewAdapter(private var savedList: ArrayList<SavedEventData>) :
    RecyclerView.Adapter<SavedEventRecyclerViewAdapter.SavedEventViewHolder>() { // has to extend RecyclerView.Adapter - StackOverflow

    private val db = Firebase.firestore // initialize the db
    private val currentUser = FirebaseAuth.getInstance().currentUser // get the current user

    inner class SavedEventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventImage = itemView.findViewById<ImageView>(R.id.FimageView)
        val eventName = itemView.findViewById<TextView>(R.id.FeventName_textView)
        val eventLocation = itemView.findViewById<TextView>(R.id.FeventLocation_textView)
        val eventDate = itemView.findViewById<TextView>(R.id.FeventDate_textView)
        val priceRange = itemView.findViewById<TextView>(R.id.FpriceRange_textView)
        val seeEventBtn = itemView.findViewById<Button>(R.id.FseeEvent_button)
        val deleteEventBtn = itemView.findViewById<Button>(R.id.deleteEvent_button)

        init {
            seeEventBtn.setOnClickListener {
                val position = adapterPosition
                val currentItem = savedList[position]
                val url = currentItem.eventUrl
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                itemView.context.startActivity(intent)
            }

            deleteEventBtn.setOnClickListener {
                val position = adapterPosition
                val currentItem = savedList[position]
                val documentId = currentItem.documentId // replace with the actual id of the document

                if (documentId != null) {
                    db.collection("userProfiles")
                        .document(currentUser?.uid.toString())
                        .collection("savedEvents")
                        .document(documentId)
                        .delete()
                        .addOnSuccessListener {
                            Log.d(TAG, "DocumentSnapshot successfully deleted!")
                            // Remove the item from the list and notify the adapter
                            savedList.removeAt(position)
                            notifyItemRemoved(position)
                        }
                        .addOnFailureListener { e: Exception -> Log.w(TAG, "Error deleting document", e) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedEventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fav_row_item, parent, false)
        return SavedEventViewHolder(view)
    }


    override fun onBindViewHolder(holder: SavedEventViewHolder, position: Int) {
        val currentItem = savedList[position]

        holder.eventName.text = currentItem.eventName ?: "TBA"
        holder.eventLocation.text = currentItem.location ?: "TBA"
        holder.eventDate.text = currentItem.date ?: "TBA"
        holder.priceRange.text = currentItem.priceRange ?: "TBA"

        val imageUrl = currentItem.eventImage
        if (imageUrl != null) {
            Glide.with(holder.itemView)
                .load(imageUrl)
                .error(R.drawable.default_image) // any error including null will use the default image
                .into(holder.eventImage)
        }
    }

    override fun getItemCount(): Int {
        return savedList.size
    }
}

/*
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

            holder.imageUrl = bestImageUrl
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
 */