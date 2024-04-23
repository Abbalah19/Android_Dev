package com.example.cs414_final
/* TODO
save event button:
X   Strings with a '/' break the app, need to sanitize the strings

    How to get the image url? toString doesn't work

    Check the price range, if = $0.0 - $0.0, hide the field, have to do this in saveEventIntent?
        maybe just don't add it if the field is empty? can I check if it is already invisible?
        -there is a getVisibility method - just as easy to check string == string

    If events have the same title, what happens in the db? - it will overwrite the existing event

X   IDE wants addition to db to be wrapped in a null catch, the user should never be able to click
X       the button if they are not logged in but I guess it doesn't check that.

XTeacher Feedback:
X    move listeners to the ViewHolder and remove from onBindViewHolder

App is slower now? Calls to Firebase and FireAuth are async, maybe its locking up the main thread?
    logcat shows timeouts on some calls to Master API, especially when clicking search and near me
    close together.
 */


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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
        val saveEvent = itemView.findViewById<Button>(R.id.saveEvent_button)
        // I need to save the image URL, add it here then collect it in the onBindViewHolder
        // then I should be able to grab it and add it to firestore
        var imageUrl: String? = null


        init {
            seeEvent.setOnClickListener {
                val position = adapterPosition
                val currentItem = eventsList[position]
                val url = currentItem._embedded.venues[0].url ?: currentItem.url
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                itemView.context.startActivity(intent)
            }

            saveEvent.setOnClickListener {
                // Create a SavedEventData object, if I pull them from the row_item.xml I should be
                // to get the strings right from the views
                // How do I get the image? toString doesn't get the URL?
                //val eventImage = eventImage.
                val eventName = eventTitle.text.toString().replace("/", "")
                val eventLocation = eventLocation.text.toString()
                val eventDate = eventDate.text.toString()
                val eventPriceRange = eventPriceRange.text.toString()

                // create an object to save the data in, idk if its needed but it seems cleaner then
                // just saving the strings directly.
                val savedEvent = SavedEventData().apply {
                    this.eventImage = imageUrl // take from holder, add to firebase
                    this.eventName = eventName
                    this.loaction = eventLocation
                    this.date = eventDate
                    this.priceRange = eventPriceRange
                }

                // Save the event object to Firestore under the current user's document
                val db = FirebaseFirestore.getInstance()
                val currentUser = FirebaseAuth.getInstance().currentUser
                // .add creates a random ID
                if (currentUser != null && savedEvent.eventName != null) {
                    db.collection("userProfiles").document(currentUser.uid)
                        .collection("savedEvents").document(savedEvent.eventName!!)
                        .set(savedEvent)
                        .addOnSuccessListener { Log.d("Firestore", "DocumentSnapshot successfully written!") }
                        .addOnFailureListener { e -> Log.w("Firestore", "Error writing document", e) }
                }
            }
        }
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