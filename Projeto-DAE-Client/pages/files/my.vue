<template>
<div>
  <div class="p-4">
    <b-container class="modal-content rounded-6 shadow p-4" >
      <h1 style="text-align:center">Os meus ficheiros</h1>
      <form @submit.prevent="upload">
        <!-- Styled -->
        <b-form-file
          v-model="file"
          :state="hasFile"
          placeholder="Choose a file or drop it here..."
          drop-placeholder="Drop file here..."
        ></b-form-file>
        <div class="mt-3">
          Selected file: {{ file ? file.name : '' }}
        </div>
        <nuxt-link class="btn btn-link" :to="`/students/`">Back</nuxt-link>
        <b-button type="submit" :disabled="!hasFile">Upload</b-button>
      </form>
    </b-container>
  </div>
  <div class="p-4">
    <b-container  class="modal-content rounded-6 shadow p-4">
      <h4>Student Details</h4>

      <h4>Subjects</h4>
      <b-table v-if="true" striped over :items="subjects" :fields="subjectFields" />
      <p v-else>No subjects enrolled.</p>
      <h4>Documents</h4>
      <b-table v-if="true" striped over :items="documents"
               :fields="documentsFields">
        <template v-slot:cell(actions)="row">
          <b-btn class="btn btn-link" @click.prevent="download(row.item)"
                 target="_blank">Download</b-btn>
        </template>
      </b-table>
      <p v-else>No documents.</p>
      <nuxt-link to="/students">Back</nuxt-link>
      &nbsp;
      <nuxt-link :to="`/students/upload`">Upload</nuxt-link>
    </b-container>
  </div>
</div>
</template>
<script>
export default {
  auth: false,
  data() {
    return {
      username: this.$auth.user.sub,
      user: null,
      file: null,
      documents: []
    }
  },
  computed: {
    hasFile () {
      return this.file != null
    },
    formData () {
      let formData = new FormData()
      formData.append('username', this.$auth.user.sub)
      if (this.file) {
        formData.append('file', this.file)
      }
      return formData
    }
  },
  created() {
    this.$axios.$get('/api/user/'+this.$auth.user.sub+'/registers')
      .then((user) => {
        this.user = user
        this.$axios.$get('/api/documents/'+this.$auth.user.sub)
          .then((documents) => {
            this.documents = documents

          })
      })
  },

  methods: {
    upload() {
      if (!this.hasFile) {
        return
      }
      let promisse = this.$axios.$post('/api/documents/upload', this.formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
      promisse.then(() => {
        this.$toast.success('File uploaded!').goAway(3000)
      })
      promisse.catch(() => {
        this.$toast.error('Sorry, could no upload file!').goAway(3000)
      })
    }
  }
}
</script>
