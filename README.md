# ScaleSelector

A simple library to make a selection RecyclerView using just a few lines of XML code.



Gradle Dependency

         implementation 'com.github.Priyansh-Kedia:ScaleSelector:0.1.4'

Add     
     
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
       
in the root build.gradle at the end of repositories



![](https://s7.gifyu.com/images/WhatsApp-Video-2020-06-21-at-12.35.21-AM.gif)




The indicator arrow in the bottom can be removed by setting showArrowPointer = false


![](https://s7.gifyu.com/images/WhatsApp-Video-2020-06-20-at-5.03.02-PM.gif)



Background color, text color and pointer line colors are also customisable

![](https://s7.gifyu.com/images/WhatsApp-Video-2020-06-20-at-5.07.59-PM.gif)



Allowed Attributes for normal scale selector

        <attr name="stepValue" format="integer" />
        <attr name="defaultTextColor" format="color" />
        <attr name="selectedTextColor" format="color" />
        <attr name="selectedPointerColor" format="color" />
        <attr name="minValue" format="integer" />
        <attr name="maxValue" format="integer" />
        <attr name="defaultPointerColor" format="color" />
        <attr name="backgroundColor" format="color" />
        <attr name="showArrowPointer" format="boolean" />
        <attr name="arrowPointerTint" format="color" />
	
	
Allowed Attributes for Circular Scale Selector

	<attr name="circleDefaultTextColor" format="color" />
        <attr name="circleSelectedTextColor" format="color" />
        <attr name="circleMinValue" format="integer" />
        <attr name="circleMaxValue" format="integer" />
        <attr name="circleBackgroundColor" format="color" />
        <attr name="selectedCircleColor" format="color" />
	
	
Simple XML use for normal selector


	<com.kedia.scaleselector.ScaleSelector
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/testRecycler"
        app:showArrowPointer="true"
        app:minValue="100"
        app:arrowPointerTint="#000000"
        app:maxValue="200"
        app:defaultPointerColor="#6F86D6"
        app:stepValue="10"
        app:selectedTextColor="#fff"
        app:defaultTextColor="#fff" />
	
	
Simple XML use for circular selector

	<com.kedia.scaleselector.CircularScaleSelector
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/demoId"
        app:circleMinValue="100"
        app:circleSelectedTextColor="#000000"
        app:selectedCircleColor="@color/blue"
        app:circleDefaultTextColor="@color/blue"
        app:circleBackgroundColor="@color/colorPrimaryDark"
        app:circleMaxValue="200"/>

	
Get the current selected value by using
	
	testRecycler.getSelectedValue()
	demoId.getSelectedValue()
	
	
To get the selected values as soon as the user taps,  the activity/ fragment should implement
	
	CircularScaleSelector.onClick or ScaleSelector.onClick or both
	
	
### Found this library useful? :heart:

Support it by joining [stargazers](https://github.com/Priyansh-Kedia/ScaleSelector/stargazers) for this repository. :star2:

	
