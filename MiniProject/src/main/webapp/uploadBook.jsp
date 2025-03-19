<div class="uploadBook" >
    <h1>Upload book review</h1>

    <div class="card shadow-sm p-4">
        <h3 class="mb-3">Create a New Book</h3>
        <form action="PublisherServlet" method="post" enctype="multipart/form-data"> 
            <div class="mb-3">
                <label for="txtTitle" class="form-label">Title</label>
                <input type="text" class="form-control" id="txtTitle" name="txtTitle" placeholder="Enter Title..." required>
            </div>
            <div class="mb-3">
                <label for="txtAuthor" class="form-label">Author</label>
                <input type="text" class="form-control" id="txtAuthor" name="txtAuthor" placeholder="Enter Author..." required>
            </div>

            <div class="mb-3">
                <label for="txtEdition" class="form-label">Edition</label>
                <input type="number" class="form-control" id="txtEdition" name="txtEdition" placeholder="Enter Edition..." required>
            </div>
            <div class="mb-3">
                <label for="txtGenre" class="form-label">Genre</label>
                <input type="text" class="form-control" id="txtGenre" name="txtGenre" placeholder="Enter Genre..." required>
            </div>
            <input type="hidden" name="txtPublisher" value="${sessionScope.publisher.userId.userId}"/>

            <div class="mb-3">
                <label for="file" class="form-label">Upload File</label>
                <input type="file" class="form-control" id="file" name="file" required>
            </div>
            <input type="submit" class="btn btn-primary" name="action" value="Create"/>
        </form>

    </div>

</div>
