<template>
  <b-container>
    <h3 style="text-align: center">Login into Academics Management</h3>
      <b-form @submit.prevent="onSubmit" @reset="onReset">
        <b-form-group label="Username" description="Enter your username">
          <b-input
            name="username"
            placeholder="Your username"
            v-model.trim="username"
            required />
        </b-form-group>
        <b-form-group label="Password" description="Enter your password">
          <b-input
            name="password"
            type="password"
            placeholder="Your password"
            v-model="password"
            required />
        </b-form-group>
        <b-button type="reset" class="btn-warning">Reset</b-button>
        <b-button type="submit" class="btn-success">Submit</b-button>
      </b-form>

    <div lg="6" class="pb-4 p-12">
    </div>
    <nuxt-link class="btn btn-dark btn-sm pd-4 p-4" to="/auth/register/"  >Criar Conta</nuxt-link>

  </b-container>
</template>
<script>
export default {
  auth: false,
  data() {
    return {
      username: null,
      password: null
    }
  },created () {
    if(this.$auth.user!=null){
      this.$axios.$get('/api/user/'+this.$auth.user.sub+'/registers')
        .then((user) => {
          this.user = user
          if(user!=null){
            this.$router.push('/')
            console.log("Im here"+ this.user)
          }
          console.log(user)
        })
    }

  },
  methods: {
    onSubmit() {
      let promise = this.$auth.loginWith('local', {
        data: {
          username: this.username,
      password: this.password
    }
    })
      promise.then(() => {
        this.$toast.success('You are logged in!').goAway(3000)
        // check if the user $auth.user object is set
        console.log(this.$auth.user)
        // TODO redirect based on the user role
        // eg:
        if (this.$auth.user.groups.includes('UtilizadorNormal') || this.$auth.user.groups.includes('Administrador')|| this.$auth.user.groups.includes('Doutor')) {
          this.$router.push('/')
        }
      })
      promise.catch(() => {
        this.$toast.error('Sorry, you cant login. Ensure your credentials are correct').goAway(3000)
      })
    },
    onReset() {
      this.username = null
      this.password = null
    }
  }
}
</script>
