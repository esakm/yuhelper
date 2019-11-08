let courseDescription = document.getElementById("course-description");
let courseDescriptionText = courseDescription.innerText;

let index = courseDescriptionText.search("Prerequisites:");
let index2 = courseDescriptionText.search("Prerequisite:");

let index3 = courseDescriptionText.search("Course credit exclusion:");
let index4 = courseDescriptionText.search("Course credit exclusions:");

let prereqs1 = courseDescriptionText.slice(index, courseDescriptionText.length);
let prereqs2 = courseDescriptionText.slice(index2, courseDescriptionText.length);

let exclusions1 = courseDescriptionText.slice(index3, courseDescriptionText.length);
let exclusions2 = courseDescriptionText.slice(index4, courseDescriptionText.length);
if(index !== -1){
    courseDescription.innerHTML = courseDescriptionText.replace(prereqs1, "<b class='text-warning'>" + prereqs1 + "</b>")
}else if(index2 !== -1){
    courseDescription.innerHTML = courseDescriptionText.replace(prereqs2, "<b class='text-warning'>" + prereqs2 + "</b>")
}else if(index3 !== -1){
    courseDescription.innerHTML = courseDescriptionText.replace(exclusions1, "<b class='text-warning'>" + exclusions1 + "</b>")
}else if(index4 !== -1){
    courseDescription.innerHTML = courseDescriptionText.replace(exclusions2, "<b class='text-warning'>" + exclusions2 + "</b>")
}
